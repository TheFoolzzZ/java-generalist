package org.geektimes.projects.spring.cloud.config.client;

import org.apache.commons.io.FilenameUtils;
import org.geektimes.projects.spring.cloud.config.client.env.ReloadablePropertiesPropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

@Order(0)
public class FileSystemPropertySourceLocator implements PropertySourceLocator {

    private static final Logger logger = LoggerFactory.getLogger(FileSystemPropertySourceLocator.class);

    private static final String FILE_PATH = "META-INF/config/";

    private static final String DEFAULT_CONFIG_FILE = "default.properties";

    private static final String[] PROPERTIES_FILE_EXTENSION = new String[]{"properties"};
    private static final String[] YAML_FILE_EXTENSION = new String[]{"yaml", "yml"};

    private Environment environment;
    private ReloadablePropertiesPropertySource propertySource;

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public FileSystemPropertySourceLocator() {
        try {
            FileWatch watch = new FileWatch(new ClassPathResource(FILE_PATH).getURI(), this);
            watch.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PropertySource<?> locate(Environment environment) {
        setEnvironment(environment);
        String profile = getProfile();

        Resource resource = getConfigFile(profile);

        try {
            List<ReloadablePropertiesPropertySource> propertySourceList = null;
            if (isProperties(resource.getFilename())) {
                propertySourceList = buildPropertiesPropertySource(resource, profile);
            } else if (isYaml(resource.getFilename())) {
                propertySourceList = buildYamlPropertySource(resource, profile);
            } else {
                throw new RuntimeException("不支持的配置文件，请使用properties 或者 yaml 格式");
            }

            if (!propertySourceList.isEmpty()) {
                this.propertySource =propertySourceList.get(0);
                return propertySource;
            }
            logger.error("读取配置文件" + resource.getFilename() + "异常");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("读取配置文件" + resource.getFilename() + "异常");
        }
        return null;

    }

    private Resource getConfigFile(String profile) {
        //查找 profile.properties
        String fileName = FILE_PATH + profile + ".properties";
        ClassPathResource resource = new ClassPathResource(fileName);
        if (!resource.isFile()) {
            fileName = FILE_PATH + profile + ".yaml";
            resource = new ClassPathResource(fileName);
            if (!resource.isFile()) {
                fileName = FILE_PATH + profile + ".yml";
                resource = new ClassPathResource(fileName);
                if (!resource.isFile()) {
                    throw new RuntimeException("配置文件目录 " + FILE_PATH + " 下没找到配置文件 ");
                }
            }
        }

        return resource;
    }

    private boolean isProperties(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);
        return Arrays.asList(PROPERTIES_FILE_EXTENSION).contains(extension);
    }

    private boolean isYaml(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);
        return Arrays.asList(YAML_FILE_EXTENSION).contains(extension);
    }

    private List<ReloadablePropertiesPropertySource> buildYamlPropertySource(Resource resource, String profile) throws IOException {
        logger.info("yaml 格式文件目前暂未实现 ");
        return Collections.emptyList();
    }

    private List<ReloadablePropertiesPropertySource> buildPropertiesPropertySource(Resource resource, String profile) throws IOException {
        Properties properties = new Properties();
        properties.load(resource.getInputStream());

        ReloadablePropertiesPropertySource propertySource = new ReloadablePropertiesPropertySource(profile, properties);
        List<ReloadablePropertiesPropertySource> propertySources = new ArrayList<>(properties.size());
        propertySources.add(propertySource);
        return propertySources;
//        return new PropertiesPropertySourceLoader().load(profile, resource);
    }

    private String getProfile() {
        String[] profiles = this.environment.getActiveProfiles();
        if (profiles.length == 0) {
            profiles = environment.getDefaultProfiles();
        }
        return profiles[0];
    }

    public void reload() throws IOException {
        String profile = getProfile();
        Resource resource = getConfigFile(profile);
        Properties properties = new Properties();
        properties.load(resource.getInputStream());
        this.propertySource.reload(properties);
    }
    class FileWatch extends Thread {
        private final FileSystemPropertySourceLocator locator;
        private final WatchKey watchKey;

        public FileWatch(URI configPath, FileSystemPropertySourceLocator locator) throws IOException{
            this.locator = locator;
            logger.info("watch file path {}", configPath);
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(configPath);
            watchKey = path.register(watchService, ENTRY_MODIFY);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    List<WatchEvent<?>> events = watchKey.pollEvents();
                    events.forEach(watchEvent -> {
                        logger.info(watchEvent.kind().toString());
                        if (watchEvent.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                            //文件发生变化，重新加载
                            String fileName = watchEvent.context().toString();
                            logger.info("文件发生变化...{}", fileName);
                            try {
                                this.locator.reload();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    logger.info("目录监听中...");
                    Thread.sleep(10 * 1000);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}