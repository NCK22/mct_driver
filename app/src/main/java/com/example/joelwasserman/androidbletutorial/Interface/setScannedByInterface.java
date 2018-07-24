package com.example.joelwasserman.androidbletutorial.Interface;

import com.example.joelwasserman.androidbletutorial.Pojo.CommonParentPojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface setScannedByInterface {

    String user_id="";
   /* @GET("Parents/list/{parent_id}")
    Call<ParentPojoLogin> doGetListResources(@Path("parent_id") String parent_id);
*/

   @FormUrlEncoded
   @POST("driver/scannedchild/")
   Call<CommonParentPojo> doGetListResources(@Field("child_mac_id") String child_mac_id, @Field("scannedby_id") String scannedby_id, @Field("scannedby") String scanned_by);

}
