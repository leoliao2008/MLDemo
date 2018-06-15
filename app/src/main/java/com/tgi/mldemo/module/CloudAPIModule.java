package com.tgi.mldemo.module;

public class CloudAPIModule {
//    private android.content.Context mContext;
//    private CloudAPIModuleCallback mCallback;
//    private static Handler mHandler;
//
//    public CloudAPIModule(android.content.Context context, CloudAPIModuleCallback callback) {
//        mContext=context;
//        mCallback = callback;
//        if(Looper.myLooper()==null){
//            Looper.prepare();
//            Looper.loop();
//        }
//        mHandler=new Handler();
//
//    }
//
//    public void identifyByBitmap(Bitmap bitmap){
//        new ImageAnnotationTask(bitmap,mCallback).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
//    }
//
//
//
//
//
//    private void showLog(String msg){
//        Log.e(getClass().getSimpleName(),msg);
//    }
//
//    private static class ImageAnnotationTask extends AsyncTask<Void,Void,List<AnnotateImageResponse>>{
//        private Bitmap mBitmap;
//        private CloudAPIModuleCallback mCallback;
//
//        public ImageAnnotationTask(Bitmap bitmap,CloudAPIModuleCallback callback) {
//            mBitmap = bitmap;
//            mCallback=callback;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mCallback.onPreExecute();
//        }
//
//        @Override
//        protected List<AnnotateImageResponse> doInBackground(Void... voids) {
//            try {
//                mBitmap=scaleBitmapDown(mBitmap);
//                ArrayList<AnnotateImageRequest> rqList=new ArrayList<>();
//                AnnotateImageRequest.Builder rqBuilder = AnnotateImageRequest.newBuilder();
//                Feature f1= Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
//                Feature f2= Feature.newBuilder().setType(Feature.Type.WEB_DETECTION).build();
//                Image image = Image.newBuilder().setContent(ByteString.copyFrom(bitmap2ByteArray(mBitmap))).build();
//                AnnotateImageRequest request = rqBuilder.addFeatures(f1).addFeatures(f2).setImage(image).build();
//                rqList.add(request);
//                ImageAnnotatorClient client = ImageAnnotatorClient.create(initImageAnnotatorSetting());
//                BatchAnnotateImagesResponse response = client.batchAnnotateImages(rqList);
//                List<AnnotateImageResponse> rpList = response.getResponsesList();
//                client.close();
//                return rpList;
//            }catch (Exception e){
//                handleExceptionThread(e);
//            }
//            return null;
//        }
//
//        private ImageAnnotatorSettings initImageAnnotatorSetting() throws IOException {
//            ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(new Credentials() {
//                @Override
//                public String getAuthenticationType() {
//                    return null;
//                }
//
//                @Override
//                public Map<String, List<String>> getRequestMetadata(URI uri) throws IOException {
//                    return null;
//                }
//
//                @Override
//                public boolean hasRequestMetadata() {
//                    return false;
//                }
//
//                @Override
//                public boolean hasRequestMetadataOnly() {
//                    return false;
//                }
//
//                @Override
//                public void refresh() throws IOException {
//
//                }
//            })).build();
//            return settings;
//        }
//
//        @Override
//        protected void onPostExecute(List<AnnotateImageResponse> list) {
//            super.onPostExecute(list);
//            if(list==null){
//                mCallback.onError(new Exception("No search result from google server due to exception."));
//                return;
//            }
//            if(list.size()<1){
//                mCallback.onError(new Exception("Search result count from google server is 0."));
//                return;
//            }
//            AnnotateImageResponse response = list.get(0);
//            if(response.hasError()){
//                mCallback.onError(new Exception("Search result from google server contains error: "+response.getError().getMessage()));
//                return;
//            }
//            WebDetection webDetection = response.getWebDetection();
//            List<EntityAnnotation> annotations = response.getLabelAnnotationsList();
//            mCallback.onPostExecute(new CloudVisionResponseBeanLite(webDetection,annotations));
//        }
//
//        private void handleExceptionThread(final Exception e){
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    mCallback.onError(e);
//                }
//            });
//        }
//
//        private void handleException(Exception e){
//            mCallback.onError(e);
//        }
//
//        /**
//         * transform the bitmap into a Base64 encoded string in order to upload it as part of the
//         * jason string.
//         * @param bitmap the target bitmap which is later to upload to google sever.
//         * @return a base64 encoded string generate from the bitmap.
//         */
//        private String bitmap2String(Bitmap bitmap) {
//            ByteArrayOutputStream out=new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            String result = Base64.encodeBase64String(out.toByteArray());
//            try {
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return result;
//        }
//
//        private byte[] bitmap2ByteArray(Bitmap bitmap){
//            ByteArrayOutputStream out=new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            byte[] bytes = out.toByteArray();
//            try {
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return bytes;
//        }
//
//        /**
//         * in case the size of bitmap is too large, we need to shrink it to a acceptable scale because
//         * we will need to upload this bitmap in string form to google server.
//         * @param bitmap the origin bitmap, its size may be very large.
//         * @return the resized bitmap, easy to upload.
//         */
//        private Bitmap scaleBitmapDown(Bitmap bitmap) {
//            int w = bitmap.getWidth();
//            int h = bitmap.getHeight();
//            int resizeW=600;//this value is considered suitable because google demo uses the same value.
//            int resizeH=600;
//            if(h>w){
//                resizeW=resizeH*w/h;
//            }else if(w>h){
//                resizeH=resizeW*h/w;
//            }
//            return Bitmap.createScaledBitmap(bitmap,resizeW,resizeH,false);
//        }
//    }

}
