package com.example.joelwasserman.androidbletutorial.Interface;

import com.example.joelwasserman.androidbletutorial.Pojo.ParentPojoGetStudProfile;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface getStudProfileInterface {

    String user_id="";
   /* @GET("Parents/list/{parent_id}")
    Call<ParentPojoLogin> doGetListResources(@Path("parent_id") String parent_id);
*/

   @FormUrlEncoded
   @POST("child/profile/")
   Call<ParentPojoGetStudProfile> doGetListResources(@Field("child_id") String username);


}
