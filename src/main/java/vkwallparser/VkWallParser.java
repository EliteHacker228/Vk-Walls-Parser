package vkwallparser;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.wall.WallPostFull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VkWallParser {
    private int appId;
    private String serviceToken;
    private String groupId;
    private String[] groupIds;
    private VkApiClient vk;
    private ServiceActor serviceActor;

    public VkWallParser(int appId, String serviceToken) {
        this.appId = appId;
        this.serviceToken = serviceToken;
        this.vk = new VkApiClient(HttpTransportClient.getInstance());
        this.serviceActor = new ServiceActor(appId, serviceToken);
    }

//    public String[] getRawPosts(String groupId, int offset, int count){
//        String[] rawPosts = new String[1];
//        try {
//            rawPosts = vk.wall().get(serviceActor).domain(groupId).offset(offset).count(count).execute()
//                    .getItems().stream().map(WallPostFull::toString).toArray(String[]::new);
//        } catch (ApiException e) {
//            switch (e.getCode()){
//                case 18:
//                    rawPosts[0] = "Ошибка: страница удалена или заблокирована";
//                case 19:
//                    rawPosts[0] = "Ошибка: контент недоступен";
//                case 30:
//                    rawPosts[0] = "Ошибка: приватный профиль";
//            }
//        } catch (ClientException e) {
//            rawPosts[0] = "Ошибка: " + e.getMessage();
//        }
//        return rawPosts;
//    }

    public VkPost[] getVkPosts(String groupId, int offset, int count) throws ClientException, ApiException {
        VkPost[] vkPosts  = vk.wall().get(serviceActor).domain(groupId).offset(offset).count(count)
                .execute().getItems().stream().map(VkPost::new).toArray(VkPost[]::new);

        return vkPosts;
    }

    public VkPost[] getVkPosts(int groupId, int offset, int count) throws ClientException, ApiException {
        VkPost[] vkPosts  = vk.wall().get(serviceActor).ownerId(-groupId).offset(offset).count(count)
                .execute().getItems().stream().map(VkPost::new).toArray(VkPost[]::new);

        return vkPosts;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String[] getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String[] groupIds) {
        this.groupIds = groupIds;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getServiceToken() {
        return serviceToken;
    }

    public void setServiceToken(String serviceToken) {
        this.serviceToken = serviceToken;
    }
}
