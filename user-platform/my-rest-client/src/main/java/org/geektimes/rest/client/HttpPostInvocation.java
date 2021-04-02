package org.geektimes.rest.client;

import org.geektimes.rest.core.DefaultResponse;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.logging.Logger;

/**
 * @description
 * @Author TheFool
 * @Date 2021/4/2 22:40
 */
class HttpPostInvocation implements Invocation {

    private final Logger logger = Logger.getLogger(HttpPostInvocation.class.getName());

    private final URI uri;

    private final URL url;

    private final MultivaluedMap<String, Object> headers;

    private final Entity<?> entity;

    HttpPostInvocation(URI uri, MultivaluedMap<String, Object> headers, Entity<?> entity) {
        this.uri = uri;
        this.headers = headers;
        this.entity = entity;
        try {
            this.url = uri.toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException();
        }
    }


    @Override
    public Invocation property(String name, Object value) {
        return this;
    }

    @Override
    public Response invoke() {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HttpMethod.POST);
            connection.setDoInput(Boolean.TRUE);
            connection.setDoOutput(Boolean.TRUE);
            connection.getOutputStream().write(objectToBytes(entity).get());
            connection.getOutputStream().flush();
            connection.getOutputStream().close();
            this.setRequestHeaders(connection);

            int statusCode = connection.getResponseCode();
//            Response.ResponseBuilder responseBuilder = Response.status(statusCode);
//            responseBuilder.build();
            DefaultResponse response = new DefaultResponse();
            response.setConnection(connection);
            response.setStatus(statusCode);
            Response.Status status = Response.Status.fromStatusCode(statusCode);
            switch (status) {
                case OK:
                    break;
                default:
                    break;
            }
            return response;
        } catch (IOException e) {
            // TODO Error handler
            logger.warning(String.format("[%s] exception: %s",this.getClass().getName(), e.getMessage())+e);
        }
//        return Response.Fail;
        return null;
    }

    private void setRequestHeaders(HttpURLConnection connection) {
        for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
            for (Object headerValue : entry.getValue()) {
                connection.setRequestProperty(entry.getKey(), headerValue.toString());
            }
        }
    }

    @Override
    public <T> T invoke(Class<T> responseType) {
        Response response = invoke();
        return response.readEntity(responseType);
    }

    @Override
    public <T> T invoke(GenericType<T> responseType) {
        Response response = invoke();
        return response.readEntity(responseType);
    }

    @Override
    public Future<Response> submit() {
        return null;
    }

    @Override
    public <T> Future<T> submit(Class<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> submit(GenericType<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> submit(InvocationCallback<T> callback) {
        return null;
    }

    public static<T> Optional<byte[]> objectToBytes(T obj){
        byte[] bytes = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream sOut;
        try {
            sOut = new ObjectOutputStream(out);
            sOut.writeObject(obj);
            sOut.writeUTF("UTF-8");
            sOut.flush();
            bytes= out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(bytes);
    }
}
