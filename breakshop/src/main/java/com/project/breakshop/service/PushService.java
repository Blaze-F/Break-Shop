package com.project.breakshop.service;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Log4j2
//TODO Firebase 관련 설정
public class PushService {

    @PostConstruct
    public void init() throws IOException {
/*
        InputStream fileStream = new FileInputStream("firebaseSDK.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials
                .fromStream(fileStream))
            .build();
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
*/
    }

/*
    public void sendMessages(List<Message> messages) {
        FirebaseMessaging.getInstance().sendAllAsync(messages);
    }
*/


}
