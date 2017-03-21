package demo.minisheep.com.photo.fragment;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import demo.minisheep.com.photo.R;
import demo.minisheep.com.photo.activity.ShowViewActivity;
import demo.minisheep.com.photo.application.RegoApplication;

import static android.R.attr.bitmap;
import static android.app.Activity.RESULT_OK;

/**
 * Created by minisheep on 17/3/8.
 */
public class FromGalleryFragment extends BaseFragment {
    private View view;
    private ImageView iv_from_album;
    private Uri imageUri;

    public static final int CROP_PHOTO = 2;  //裁剪
    public static final int CHOOSE_PHOTO = 3;  //图库选择图片

    @Override
    public View initView() {
        TextView tv = new TextView(mContext);
        tv.setText("图库导入");
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(40);
        return tv;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null)
            view = inflater.inflate(R.layout.fromalbum,container,false);
        ViewGroup parent = (ViewGroup) view.getParent();
        if(parent != null){
            parent.removeView(view);
        }

        setView();
        return view;
    }

    private void setView() {
        iv_from_album = (ImageView) view.findViewById(R.id.iv_from_album);

        iv_from_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_PHOTO);
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if(resultCode == RESULT_OK){
                    String imagePath = "";
                    Bitmap bitmap;
                    if(Build.VERSION.SDK_INT >= 19){
                        imagePath = handleImageOnKitKat(data);
                        if(imagePath!=null) {
                            bitmap = BitmapFactory.decodeFile(imagePath);
                        }else{
                            bitmap = null;
                        }
                    }else{
                        imagePath = handleImageBeforeKitKat(data);
                        if(imagePath!=null) {
                            bitmap = BitmapFactory.decodeFile(imagePath);
                        }else{
                            bitmap = null;
                        }
                    }
                    System.out.println("路径为:" + imagePath);
                    File outputImage = new File(imagePath);
                    imageUri = Uri.fromFile(outputImage);
                    System.out.println("URL为:" + imageUri);
                    if(bitmap != null){
                        //((RegoApplication)getActivity().getApplication()).setBitmap(bitmap);
//                        Intent intent = new Intent(getActivity(), ShowViewActivity.class);
//                        startActivity(intent);
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(imageUri, "image/*");
                        intent.putExtra("scale", true);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, CROP_PHOTO);
                    }else{
                        //getActivity().finish();
                        Toast.makeText(getActivity(),"图片打开失败,请确认图片是否损坏",Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    //裁剪前的图片
                    //System.out.println("CROP_PHOTO");
                    Bitmap bitmapBeforeCut = null;
                    try {
                        bitmapBeforeCut = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    //Bitmap bitmapBeforeCut = ((RegoApplication) getActivity().getApplication()).getBitmaps();
                    ((RegoApplication)getActivity().getApplication()).setBitmap(bitmapBeforeCut);
                    Intent intent = new Intent(getActivity(), ShowViewActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(getActivity(),uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);


            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);

            }
            else if("content".equalsIgnoreCase(uri.getScheme())){
                imagePath = getImagePath(uri,null);

            }
            //displayImage(imagePath);
        }
        return imagePath;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        //displayImage(imagePath);
        return imagePath;
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getActivity().getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
