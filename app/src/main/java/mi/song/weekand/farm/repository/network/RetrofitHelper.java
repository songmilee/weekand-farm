package mi.song.weekand.farm.repository.network;

import retrofit2.Retrofit;

public class RetrofitHelper {
    private Retrofit retrofit;

    public DBService getDBService(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl("https://firestore.googleapis.com/v1/projects/corp-diary/databases/(default)/documents/").build();
        }

        DBService service = retrofit.create(DBService.class);

        return service;
    }
}
