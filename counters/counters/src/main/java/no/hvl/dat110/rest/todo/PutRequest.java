package no.hvl.dat110.rest.todo;

import okhttp3.*;

import java.io.IOException;

public class PutRequest {

	public static final MediaType JSON
    = MediaType.parse("application/json; charset=utf-8");
	
	public static void main(String[] args) {

		ToDoList toDo = new ToDoList();
		
		OkHttpClient client = new OkHttpClient();

		RequestBody body = RequestBody.create(JSON, toDo.toJson());
		
		Request request = new Request.Builder().url("http://localhost:8080/counters").put(body).build();

		System.out.println(request.toString());

		try (Response response = client.newCall(request).execute()) {
			System.out.println(response.body().string());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
