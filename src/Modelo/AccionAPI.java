package Modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class AccionAPI {

    private static final String API_KEY = "O4DEVOGNSINRM6TB"; //O4DEVOGNSINRM6TB //ZQGZTC9DBQEZQVPB
    private static final String BASE_URL = "https://www.alphavantage.co/query";

    public double obtenerPrecioActual(String simbolo) {
        String endpoint = BASE_URL + "?function=TIME_SERIES_INTRADAY&symbol=" + simbolo + "&interval=5min&apikey=" + API_KEY;
        try {
            // Realizar la solicitud HTTP
            URL url = new URL(endpoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // Leer la respuesta
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parsear JSON para obtener el precio actual
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONObject timeSeries = jsonResponse.getJSONObject("Time Series (5min)");
            String ultimaClave = timeSeries.keys().next(); // Último timestamp
            double precio = timeSeries.getJSONObject(ultimaClave).getDouble("4. close");

            return precio;
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Devuelve -1 si ocurre un error
        }
    }

    public boolean validarNombreEmpresa(String nombreEmpresa) {
        String endpoint = BASE_URL + "?function=SYMBOL_SEARCH" + "&keywords=" + nombreEmpresa + "&apikey=" + API_KEY;

        try {
            URL url = new URL(endpoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());

            if (jsonResponse.has("bestMatches")) {
                JSONArray bestMatches = jsonResponse.getJSONArray("bestMatches");
                return bestMatches.length() > 0;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // En caso de error, retorna false
        }
    }
}
