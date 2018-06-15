package com.tgi.mldemo.bean;

import java.util.ArrayList;

public class IdentifyImageRequestBean {
    public ArrayList<RequestsBean> requests=new ArrayList<>();

    public static class RequestsBean {
        /**
         * image : {"content":"/9j/7QBEUGhvdG9...image contents...eYxxxzj/Coa6Bax//Z"}
         * features : [{"type":"LABEL_DETECTION","maxResults":1}]
         */

        public ImageBean image;
        public ArrayList<FeaturesBean> features=new ArrayList<>();


        public static class ImageBean {
            /**
             * content : /9j/7QBEUGhvdG9...image contents...eYxxxzj/Coa6Bax//Z
             */

            public String content;

            @Override
            public String toString() {
                return "ImageBean{" +
                        "content='" + content + '\'' +
                        '}';
            }
        }

        public static class FeaturesBean {
            /**
             * type : LABEL_DETECTION
             * maxResults : 1
             */

            public String type;
            public int maxResults;

            public FeaturesBean(String type, int maxResults) {
                this.type = type;
                this.maxResults = maxResults;
            }

            @Override
            public String toString() {
                return "FeaturesBean{" +
                        "type='" + type + '\'' +
                        ", maxResults=" + maxResults +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "RequestsBean{" +
                    "image=" + image +
                    ", features=" + features +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "IdentifyImageRequestBean{" +
                "requests=" + requests +
                '}';
    }

    //    {
//        "requests":[
//        {
//            "image":{
//            "content":"/9j/7QBEUGhvdG9...image contents...eYxxxzj/Coa6Bax//Z"
//        },
//            "features":[
//            {
//                "type":"LABEL_DETECTION",
//                    "maxResults":1
//            }
//      ]
//        }
//  ]
//    }


}
