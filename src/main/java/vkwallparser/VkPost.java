package vkwallparser;

import com.google.gson.ExclusionStrategy;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.vk.api.sdk.objects.audio.Audio;
import com.vk.api.sdk.objects.docs.Doc;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.video.Video;
import com.vk.api.sdk.objects.wall.PostedPhoto;
import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.objects.wall.WallpostAttachment;

import java.util.ArrayList;
import java.util.List;

public class VkPost {
    @Expose(serialize = false)
    private WallPostFull wallPostFull;

    @Expose(serialize = false)
    private List<WallpostAttachment> wallpostAttachments;

    @Expose(serialize = false)
    private List<Photo> photoObjects = new ArrayList<>();

    @Expose(serialize = true)
    private List<String> photoLinks = new ArrayList<>();

    @Expose(serialize = true)
    private String text;

    @Expose(serialize = false)
    private List<PostedPhoto> postedPhotos = new ArrayList<>();

    @Expose(serialize = false)
    private List<Audio> audios = new ArrayList<>();

    @Expose(serialize = true)
    private List<String> audioLinks = new ArrayList<>();

    @Expose(serialize = false)
    private List<Video> videos = new ArrayList<>();

    @Expose(serialize = true)
    private List<String> videoLinks = new ArrayList<>();

    @Expose(serialize = false)
    private List<Doc> docs = new ArrayList<>();

    @Expose(serialize = true)
    private List<String> docLinks = new ArrayList<>();

    @Expose(serialize = false)
    private GsonBuilder gsonBuilder = new GsonBuilder();

    public VkPost(WallPostFull wallPostFull) {
        this.wallPostFull = wallPostFull;
        this.wallpostAttachments = wallPostFull.getAttachments();
        initPost();
    }

    private void initPost() {
        this.text = wallPostFull.getText();

        for (WallpostAttachment wallpostAttachment : this.wallpostAttachments) {

            switch (wallpostAttachment.getType()) {
                case PHOTO:
                    Photo photo = wallpostAttachment.getPhoto();
                    photoObjects.add(photo);
                    photoLinks.add(generatePhotoLink(photo));
                    break;
                case POSTED_PHOTO:
                    PostedPhoto postedPhoto = wallpostAttachment.getPostedPhoto();
                    postedPhotos.add(postedPhoto);
                    break;
                case AUDIO:
                    Audio audio = wallpostAttachment.getAudio();
                    audios.add(audio);
                    audioLinks.add(audio.getUrl());
                    break;
                case VIDEO:
                    Video video = wallpostAttachment.getVideo();
                    videos.add(video);
                    videoLinks.add(generateVideoLink(video));
                    break;
                case DOC:
                    Doc doc = wallpostAttachment.getDoc();
                    docs.add(doc);
                    docLinks.add(doc.getUrl());
                    break;
            }
        }
    }

    private String generatePhotoLink(Photo photo) {
        String linkTemplate = "https://vk.com/publicCLUB_ID?z=photo-CLUB_ID_PHOTO_ID";
        String photoLink = linkTemplate
                .replace("CLUB_ID", String.valueOf(-wallPostFull.getOwnerId()))
                .replace("PHOTO_ID", photo.getId().toString());
        return photoLink;
    }

    private String generateVideoLink(Video video) {
        String linkTemplate = "https://vk.com/publicCLUB_ID?z=video-CLUB_ID_VIDEO_ID";
        String videoLink = linkTemplate
                .replace("CLUB_ID", String.valueOf(-wallPostFull.getOwnerId())
                .replace("VIDEO_ID", video.getId().toString()));
        return videoLink;
    }

    public String getText() {
        return text;
    }

    public List<String> getPhotoLinks() {
        return photoLinks;
    }

    public List<String> getVideoLinks() {
        return videoLinks;
    }

    public List<String> getAudioLinks() {
        return audioLinks;
    }

    public List<String> getDocLinks() {
        return docLinks;
    }

    @Override
    public String toString() {
        return gsonBuilder.excludeFieldsWithoutExposeAnnotation().create().toJson(this).replace("\\u003d", "=");
    }
}
