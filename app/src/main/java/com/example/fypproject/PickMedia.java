package com.example.fypproject;//package com.example.fypproject;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.PickVisualMediaRequest;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class PickMedia extends AppCompatActivity {
//
//    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
//
//    PickMedia(){
//        pickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
//                        if (uri != null) {
//                            //callback
//                        } else {
//                            //callback
//                        }
//                    });
//    }
//
//    public void pickSingleImage() {
//        pickMedia.launch(new PickVisualMediaRequest.Builder()
//                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
//                .build());
//
//    }
//}
