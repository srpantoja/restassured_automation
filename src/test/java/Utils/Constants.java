package Utils;


import io.github.cdimascio.dotenv.Dotenv;

public class Constants {

	static Dotenv dotenv = Dotenv.load();
	public static String url = dotenv.get("URL");
	public static String token = dotenv.get("TOKEN");
	public static String endpoint_user = "user/";
	public static String endpoint_module = "api/module";

	public static String getCurrentDate() {
		return String.valueOf(System.currentTimeMillis());
	}

}
