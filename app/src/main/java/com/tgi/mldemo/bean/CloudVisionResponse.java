package com.tgi.mldemo.bean;

import java.util.List;

public class CloudVisionResponse {

    private List<Response> responses;

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public static class Response {
        /**
         * labelAnnotations : [{"mid":"/m/0k4j","description":"car","score":0.98641205,"topicality":0.98641205},{"mid":"/m/07yv9","description":"vehicle","score":0.96182334,"topicality":0.96182334},{"mid":"/m/0ncqpsr","description":"race car","score":0.8923542,"topicality":0.8923542},{"mid":"/m/012f08","description":"motor vehicle","score":0.8901705,"topicality":0.8901705},{"mid":"/m/012mq4","description":"sports car","score":0.8320954,"topicality":0.8320954},{"mid":"/m/068mqj","description":"automotive design","score":0.8199152,"topicality":0.8199152},{"mid":"/m/0dr0x","description":"sports car racing","score":0.7546777,"topicality":0.7546777},{"mid":"/m/03xkll","description":"jaguar c type","score":0.6380355,"topicality":0.6380355},{"mid":"/m/06j11d","description":"performance car","score":0.57372487,"topicality":0.57372487},{"mid":"/m/02hgxf","description":"jaguar e type","score":0.521402,"topicality":0.521402},{"mid":"/m/01xq49","description":"vintage car","score":0.519053,"topicality":0.519053}]
         * textAnnotations : [{"locale":"hu","description":"EDO\nARRA AMIRIAM AD\nA VERACRUZ\n","boundingPoly":{"vertices":[{"x":78,"y":187},{"x":577,"y":187},{"x":577,"y":309},{"x":78,"y":309}]}},{"description":"EDO","boundingPoly":{"vertices":[{"x":236,"y":226},{"x":271,"y":230},{"x":269,"y":244},{"x":234,"y":240}]}},{"description":"ARRA","boundingPoly":{"vertices":[{"x":492,"y":226},{"x":513,"y":216},{"x":520,"y":230},{"x":499,"y":240}]}},{"description":"AMIRIAM","boundingPoly":{"vertices":[{"x":516,"y":214},{"x":551,"y":196},{"x":558,"y":211},{"x":523,"y":228}]}},{"description":"AD","boundingPoly":{"vertices":[{"x":553,"y":196},{"x":570,"y":187},{"x":577,"y":201},{"x":560,"y":209}]}},{"description":"A","boundingPoly":{"vertices":[{"x":83,"y":221},{"x":103,"y":222},{"x":98,"y":291},{"x":78,"y":290}]}},{"description":"VERACRUZ","boundingPoly":{"vertices":[{"x":131,"y":224},{"x":346,"y":239},{"x":341,"y":309},{"x":126,"y":294}]}}]
         * safeSearchAnnotation : {"adult":"VERY_UNLIKELY","spoof":"VERY_UNLIKELY","medical":"VERY_UNLIKELY","violence":"VERY_UNLIKELY","racy":"VERY_UNLIKELY"}
         * imagePropertiesAnnotation : {"dominantColors":{"colors":[{"color":{"red":242,"green":243,"blue":243},"score":0.34873265,"pixelFraction":0.16379496},{"color":{"red":18,"green":21,"blue":17},"score":0.08036083,"pixelFraction":0.07598587},{"color":{"red":109,"green":127,"blue":68},"score":0.05416252,"pixelFraction":0.33761084},{"color":{"red":108,"green":26,"blue":32},"score":0.018764466,"pixelFraction":0.0020906928},{"color":{"red":169,"green":93,"blue":108},"score":0.0032884036,"pixelFraction":2.8837143E-4},{"color":{"red":219,"green":111,"blue":135},"score":0.0012333742,"pixelFraction":7.209286E-5},{"color":{"red":193,"green":196,"blue":195},"score":0.08602339,"pixelFraction":0.037488285},{"color":{"red":50,"green":53,"blue":54},"score":0.050128736,"pixelFraction":0.033451084},{"color":{"red":154,"green":158,"blue":157},"score":0.049730413,"pixelFraction":0.023574363},{"color":{"red":115,"green":120,"blue":120},"score":0.04900372,"pixelFraction":0.026097614}]}}
         * cropHintsAnnotation : {"cropHints":[{"boundingPoly":{"vertices":[{"x":166},{"x":606},{"x":606,"y":543},{"x":166,"y":543}]},"confidence":0.79999995,"importanceFraction":0.72999996},{"boundingPoly":{"vertices":[{"x":70},{"x":614},{"x":614,"y":543},{"x":70,"y":543}]},"confidence":0.79999995,"importanceFraction":0.87},{"boundingPoly":{"vertices":[{"x":62},{"x":718},{"x":718,"y":543},{"x":62,"y":543}]},"confidence":0.79999995,"importanceFraction":0.96999997}]}
         * fullTextAnnotation : {"pages":[{"property":{"detectedLanguages":[{"languageCode":"hu","confidence":0.52},{"languageCode":"es","confidence":0.36},{"languageCode":"eu","confidence":0.12}]},"width":799,"height":544,"blocks":[{"boundingBox":{"vertices":[{"x":236,"y":226},{"x":271,"y":230},{"x":269,"y":244},{"x":234,"y":240}]},"paragraphs":[{"boundingBox":{"vertices":[{"x":236,"y":226},{"x":271,"y":230},{"x":269,"y":244},{"x":234,"y":240}]},"words":[{"property":{"detectedLanguages":[{"languageCode":"eu"}]},"boundingBox":{"vertices":[{"x":236,"y":226},{"x":271,"y":230},{"x":269,"y":244},{"x":234,"y":240}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"eu"}]},"boundingBox":{"vertices":[{"x":236,"y":226},{"x":245,"y":227},{"x":243,"y":241},{"x":234,"y":240}]},"text":"E","confidence":0.97},{"property":{"detectedLanguages":[{"languageCode":"eu"}]},"boundingBox":{"vertices":[{"x":248,"y":228},{"x":258,"y":229},{"x":256,"y":243},{"x":246,"y":242}]},"text":"D","confidence":0.96},{"property":{"detectedLanguages":[{"languageCode":"eu"}],"detectedBreak":{"type":"LINE_BREAK"}},"boundingBox":{"vertices":[{"x":261,"y":229},{"x":271,"y":230},{"x":269,"y":244},{"x":259,"y":243}]},"text":"O","confidence":0.97}],"confidence":0.96}],"confidence":0.96}],"blockType":"TEXT","confidence":0.96},{"boundingBox":{"vertices":[{"x":492,"y":226},{"x":570,"y":187},{"x":577,"y":201},{"x":499,"y":240}]},"paragraphs":[{"boundingBox":{"vertices":[{"x":492,"y":226},{"x":570,"y":187},{"x":577,"y":201},{"x":499,"y":240}]},"words":[{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":492,"y":226},{"x":513,"y":216},{"x":520,"y":230},{"x":499,"y":240}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":492,"y":226},{"x":497,"y":223},{"x":504,"y":237},{"x":499,"y":239}]},"text":"A","confidence":0.38},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":499,"y":223},{"x":504,"y":220},{"x":511,"y":234},{"x":506,"y":236}]},"text":"R","confidence":0.74},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":507,"y":219},{"x":510,"y":218},{"x":516,"y":231},{"x":514,"y":232}]},"text":"R","confidence":0.56},{"property":{"detectedLanguages":[{"languageCode":"hu"}],"detectedBreak":{"type":"SPACE"}},"boundingBox":{"vertices":[{"x":510,"y":217},{"x":513,"y":216},{"x":519,"y":229},{"x":517,"y":230}]},"text":"A","confidence":0.22}],"confidence":0.47},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":516,"y":214},{"x":551,"y":196},{"x":558,"y":211},{"x":523,"y":228}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":516,"y":214},{"x":523,"y":210},{"x":530,"y":224},{"x":523,"y":227}]},"text":"A","confidence":0.24},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":526,"y":210},{"x":530,"y":208},{"x":536,"y":222},{"x":533,"y":223}]},"text":"M","confidence":0.1},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":529,"y":208},{"x":533,"y":206},{"x":539,"y":220},{"x":536,"y":221}]},"text":"I","confidence":0.72},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":535,"y":205},{"x":539,"y":203},{"x":546,"y":216},{"x":542,"y":218}]},"text":"R","confidence":0.94},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":541,"y":201},{"x":544,"y":200},{"x":550,"y":213},{"x":548,"y":214}]},"text":"I","confidence":0.11},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":545,"y":199},{"x":547,"y":198},{"x":554,"y":211},{"x":552,"y":212}]},"text":"A","confidence":0.77},{"property":{"detectedLanguages":[{"languageCode":"hu"}],"detectedBreak":{"type":"SPACE"}},"boundingBox":{"vertices":[{"x":549,"y":197},{"x":551,"y":196},{"x":558,"y":209},{"x":556,"y":210}]},"text":"M","confidence":0.02}],"confidence":0.41},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":553,"y":196},{"x":570,"y":187},{"x":577,"y":201},{"x":560,"y":209}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":553,"y":196},{"x":560,"y":192},{"x":567,"y":206},{"x":560,"y":209}]},"text":"A","confidence":0.09},{"property":{"detectedLanguages":[{"languageCode":"hu"}],"detectedBreak":{"type":"LINE_BREAK"}},"boundingBox":{"vertices":[{"x":563,"y":191},{"x":570,"y":187},{"x":577,"y":201},{"x":570,"y":204}]},"text":"D","confidence":0.03}],"confidence":0.06}],"confidence":0.37}],"blockType":"TEXT","confidence":0.37},{"boundingBox":{"vertices":[{"x":83,"y":221},{"x":346,"y":240},{"x":341,"y":310},{"x":78,"y":291}]},"paragraphs":[{"boundingBox":{"vertices":[{"x":83,"y":221},{"x":346,"y":240},{"x":341,"y":310},{"x":78,"y":291}]},"words":[{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":83,"y":221},{"x":103,"y":222},{"x":98,"y":291},{"x":78,"y":290}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"es"}],"detectedBreak":{"type":"SPACE"}},"boundingBox":{"vertices":[{"x":83,"y":221},{"x":103,"y":222},{"x":98,"y":291},{"x":78,"y":290}]},"text":"A","confidence":0.07}],"confidence":0.07},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":131,"y":224},{"x":346,"y":239},{"x":341,"y":309},{"x":126,"y":294}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":131,"y":224},{"x":154,"y":226},{"x":149,"y":294},{"x":126,"y":293}]},"text":"V","confidence":0.92},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":159,"y":226},{"x":182,"y":228},{"x":177,"y":296},{"x":154,"y":295}]},"text":"E","confidence":0.96},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":188,"y":229},{"x":208,"y":230},{"x":203,"y":299},{"x":183,"y":298}]},"text":"R","confidence":0.08},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":208,"y":229},{"x":231,"y":231},{"x":226,"y":299},{"x":203,"y":298}]},"text":"A","confidence":0.92},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":242,"y":232},{"x":267,"y":234},{"x":262,"y":303},{"x":237,"y":301}]},"text":"C","confidence":0.92},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":269,"y":234},{"x":294,"y":236},{"x":289,"y":305},{"x":264,"y":303}]},"text":"R","confidence":0.97},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":305,"y":236},{"x":328,"y":238},{"x":323,"y":306},{"x":300,"y":305}]},"text":"U","confidence":0.97},{"property":{"detectedLanguages":[{"languageCode":"es"}],"detectedBreak":{"type":"LINE_BREAK"}},"boundingBox":{"vertices":[{"x":329,"y":239},{"x":346,"y":240},{"x":341,"y":309},{"x":324,"y":308}]},"text":"Z","confidence":0.12}],"confidence":0.73}],"confidence":0.65}],"blockType":"TEXT","confidence":0.65}]}],"text":"EDO\nARRA AMIRIAM AD\nA VERACRUZ\n"}
         * webDetection : {"webEntities":[{"entityId":"/m/03xkll","score":3.413219,"description":"Jaguar C-Type"},{"entityId":"/m/0k4j","score":1.0541999,"description":"Car"},{"entityId":"/m/012x34","score":1.0011001,"description":"Jaguar Cars"},{"entityId":"/m/012mq4","score":0.9942,"description":"Sports car"},{"entityId":"/m/02hgxf","score":0.9551171,"description":"Jaguar E-Type"},{"entityId":"/m/0h5ww88","score":0.91905,"description":"Ferrari"},{"entityId":"/m/04f4xh","score":0.7791,"description":"Ferrari 365 GT4 2+2, 400 and 412"},{"entityId":"/m/02_kt","score":0.7779,"description":"Ferrari"},{"entityId":"/m/0dr0x","score":0.77439445,"description":"Sports car racing"},{"entityId":"/m/09248h","score":0.7413,"description":"Jaguar XK120"},{"entityId":"/m/0y4l_bb","score":0.7021},{"entityId":"/m/0y4l_gh","score":0.7012},{"entityId":"/m/0ltv","score":0.546,"description":"Auto racing"},{"entityId":"/m/06j11d","score":0.49187756,"description":"Performance car"},{"entityId":"/g/1q64nn5ly","score":0.4716,"description":"Ferrari Superamerica"}],"fullMatchingImages":[{"url":"https://i0.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20170205122949/d3s_6485.jpg?fit=750%2C510"}],"partialMatchingImages":[{"url":"https://i1.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20170205122954/d3s_6488.jpg?fit=750%2C514"},{"url":"https://i0.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20161213154657/quail-motorsports-gathering_2010-19.jpg?fit=750%2C498"}],"pagesWithMatchingImages":[{"url":"http://www.sportscardigest.com/jaguar-c-type-xkc-029-car-profile-and-photo-gallery/","pageTitle":"&lt;b&gt;Jaguar C-Type&lt;/b&gt; XKC 029 - Car Profile, History and Photo Gallery","fullMatchingImages":[{"url":"https://i0.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20170205122949/d3s_6485.jpg?fit=750%2C510"},{"url":"https://i1.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20170205122954/d3s_6488.jpg?fit=750%2C514"}]},{"url":"http://www.sportscardigest.com/photo-gallery/jaguar-c-type-xkc-029-photo-gallery/","pageTitle":"&lt;b&gt;Jaguar C-Type&lt;/b&gt; XKC 029 - Photo Gallery - Sports Car Digest - The ...","fullMatchingImages":[{"url":"https://i0.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20170205122949/d3s_6485.jpg?fit=750%2C510"},{"url":"https://i1.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20170205122954/d3s_6488.jpg?fit=750%2C514"}]},{"url":"http://www.sportscardigest.com/photo-gallery/quail-motorsports-gathering-2010/","pageTitle":"Quail Motorsports Gathering 2010 - Sports Car Digest - The Sports ...","partialMatchingImages":[{"url":"https://i0.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20161213154657/quail-motorsports-gathering_2010-19.jpg?fit=750%2C498"}]},{"url":"http://live-sportscardigest.sites.thewpvalet.com/photo-gallery/jaguar-c-type-xkc-029-photo-gallery/","pageTitle":"&lt;b&gt;Jaguar C-Type&lt;/b&gt; XKC 029 - Photo Gallery - Sports Car Digest - The ...","partialMatchingImages":[{"url":"https://i1.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20170205122954/d3s_6488.jpg?fit=750%2C514"}]},{"url":"http://live-sportscardigest.sites.thewpvalet.com/jaguar-c-type-xkc-029-car-profile-and-photo-gallery/","pageTitle":"&lt;b&gt;Jaguar C-Type&lt;/b&gt; XKC 029 - Car Profile, History and Photo Gallery","fullMatchingImages":[{"url":"https://i0.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20170205122949/d3s_6485.jpg?fit=750%2C510"}]},{"url":"http://www.sportscardigest.com/quail-motorsports-gathering-2010-winners-and-photo-gallery/","pageTitle":"Quail Motorsports Gathering 2010 - Winners and Photo Gallery","partialMatchingImages":[{"url":"https://i0.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20161213154657/quail-motorsports-gathering_2010-19.jpg?fit=750%2C498"}]},{"url":"http://live-sportscardigest.sites.thewpvalet.com/photo-gallery/quail-motorsports-gathering-2010/","pageTitle":"Quail Motorsports Gathering 2010 - Sports Car Digest - The Sports ...","partialMatchingImages":[{"url":"https://i0.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20161213154657/quail-motorsports-gathering_2010-19.jpg?fit=750%2C498"}]},{"url":"http://live-sportscardigest.sites.thewpvalet.com/quail-motorsports-gathering-2010-winners-and-photo-gallery/","pageTitle":"Quail Motorsports Gathering 2010 - Winners and Photo Gallery","partialMatchingImages":[{"url":"https://i0.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20161213154657/quail-motorsports-gathering_2010-19.jpg?fit=750%2C498"}]}],"visuallySimilarImages":[{"url":"https://i1.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20170205122930/d3s_6473.jpg?fit=750%2C479"},{"url":"https://photos.smugmug.com/BRITISHCLASSICCARS/JAGUAR-C-D-TYPE/i-t3f3N4c/0/0718bed3/L/PUR%20120-X-L.jpg"},{"url":"https://cdn.pixabay.com/photo/2018/04/30/20/07/racing-car-3363885_960_720.jpg"},{"url":"http://d39jj55bl6wreq.cloudfront.net/image-thumb?w=534&amp;h=345&amp;p=/files/lot-images/SA-0000867/0.jpg"},{"url":"https://39jgo82ei3833qry2p32u7p0-wpengine.netdna-ssl.com/wp-content/uploads/2017/04/1920px-Jaguar_C-Type-510x382.jpg"},{"url":"https://www.conceptcarz.com/images/Jaguar/52-Jaguar-XK120C-XKC007-DV-10-AI-05-800.jpg"},{"url":"https://www.carandclassic.co.uk/uploads/cars/jaguar/7812753.jpg"},{"url":"http://car-from-uk.com/ebay/carphotos/full/ebay1047403.jpg"}],"bestGuessLabels":[{"label":"Jaguar C-Type"}]}
         */

        private SafeSearchAnnotation safeSearchAnnotation;
        private ImagePropertiesAnnotation imagePropertiesAnnotation;
        private CropHintsAnnotation cropHintsAnnotation;
        private FullTextAnnotation fullTextAnnotation;
        private WebDetection webDetection;
        private List<LabelAnnotations> labelAnnotations;
        private List<TextAnnotations> textAnnotations;

        public SafeSearchAnnotation getSafeSearchAnnotation() {
            return safeSearchAnnotation;
        }

        public void setSafeSearchAnnotation(SafeSearchAnnotation safeSearchAnnotation) {
            this.safeSearchAnnotation = safeSearchAnnotation;
        }

        public ImagePropertiesAnnotation getImagePropertiesAnnotation() {
            return imagePropertiesAnnotation;
        }

        public void setImagePropertiesAnnotation(ImagePropertiesAnnotation imagePropertiesAnnotation) {
            this.imagePropertiesAnnotation = imagePropertiesAnnotation;
        }

        public CropHintsAnnotation getCropHintsAnnotation() {
            return cropHintsAnnotation;
        }

        public void setCropHintsAnnotation(CropHintsAnnotation cropHintsAnnotation) {
            this.cropHintsAnnotation = cropHintsAnnotation;
        }

        public FullTextAnnotation getFullTextAnnotation() {
            return fullTextAnnotation;
        }

        public void setFullTextAnnotation(FullTextAnnotation fullTextAnnotation) {
            this.fullTextAnnotation = fullTextAnnotation;
        }

        public WebDetection getWebDetection() {
            return webDetection;
        }

        public void setWebDetection(WebDetection webDetection) {
            this.webDetection = webDetection;
        }

        public List<LabelAnnotations> getLabelAnnotations() {
            return labelAnnotations;
        }

        public void setLabelAnnotations(List<LabelAnnotations> labelAnnotations) {
            this.labelAnnotations = labelAnnotations;
        }

        public List<TextAnnotations> getTextAnnotations() {
            return textAnnotations;
        }

        public void setTextAnnotations(List<TextAnnotations> textAnnotations) {
            this.textAnnotations = textAnnotations;
        }

        public static class SafeSearchAnnotation {
            /**
             * adult : VERY_UNLIKELY
             * spoof : VERY_UNLIKELY
             * medical : VERY_UNLIKELY
             * violence : VERY_UNLIKELY
             * racy : VERY_UNLIKELY
             */

            private String adult;
            private String spoof;
            private String medical;
            private String violence;
            private String racy;

            public String getAdult() {
                return adult;
            }

            public void setAdult(String adult) {
                this.adult = adult;
            }

            public String getSpoof() {
                return spoof;
            }

            public void setSpoof(String spoof) {
                this.spoof = spoof;
            }

            public String getMedical() {
                return medical;
            }

            public void setMedical(String medical) {
                this.medical = medical;
            }

            public String getViolence() {
                return violence;
            }

            public void setViolence(String violence) {
                this.violence = violence;
            }

            public String getRacy() {
                return racy;
            }

            public void setRacy(String racy) {
                this.racy = racy;
            }
        }

        public static class ImagePropertiesAnnotation {
            /**
             * dominantColors : {"colors":[{"color":{"red":242,"green":243,"blue":243},"score":0.34873265,"pixelFraction":0.16379496},{"color":{"red":18,"green":21,"blue":17},"score":0.08036083,"pixelFraction":0.07598587},{"color":{"red":109,"green":127,"blue":68},"score":0.05416252,"pixelFraction":0.33761084},{"color":{"red":108,"green":26,"blue":32},"score":0.018764466,"pixelFraction":0.0020906928},{"color":{"red":169,"green":93,"blue":108},"score":0.0032884036,"pixelFraction":2.8837143E-4},{"color":{"red":219,"green":111,"blue":135},"score":0.0012333742,"pixelFraction":7.209286E-5},{"color":{"red":193,"green":196,"blue":195},"score":0.08602339,"pixelFraction":0.037488285},{"color":{"red":50,"green":53,"blue":54},"score":0.050128736,"pixelFraction":0.033451084},{"color":{"red":154,"green":158,"blue":157},"score":0.049730413,"pixelFraction":0.023574363},{"color":{"red":115,"green":120,"blue":120},"score":0.04900372,"pixelFraction":0.026097614}]}
             */

            private DominantColorsBean dominantColors;

            public DominantColorsBean getDominantColors() {
                return dominantColors;
            }

            public void setDominantColors(DominantColorsBean dominantColors) {
                this.dominantColors = dominantColors;
            }

            public static class DominantColorsBean {
                private List<ColorsBean> colors;

                public List<ColorsBean> getColors() {
                    return colors;
                }

                public void setColors(List<ColorsBean> colors) {
                    this.colors = colors;
                }

                public static class ColorsBean {
                    /**
                     * color : {"red":242,"green":243,"blue":243}
                     * score : 0.34873265
                     * pixelFraction : 0.16379496
                     */

                    private ColorBean color;
                    private double score;
                    private double pixelFraction;

                    public ColorBean getColor() {
                        return color;
                    }

                    public void setColor(ColorBean color) {
                        this.color = color;
                    }

                    public double getScore() {
                        return score;
                    }

                    public void setScore(double score) {
                        this.score = score;
                    }

                    public double getPixelFraction() {
                        return pixelFraction;
                    }

                    public void setPixelFraction(double pixelFraction) {
                        this.pixelFraction = pixelFraction;
                    }

                    public static class ColorBean {
                        /**
                         * red : 242
                         * green : 243
                         * blue : 243
                         */

                        private int red;
                        private int green;
                        private int blue;

                        public int getRed() {
                            return red;
                        }

                        public void setRed(int red) {
                            this.red = red;
                        }

                        public int getGreen() {
                            return green;
                        }

                        public void setGreen(int green) {
                            this.green = green;
                        }

                        public int getBlue() {
                            return blue;
                        }

                        public void setBlue(int blue) {
                            this.blue = blue;
                        }
                    }
                }
            }
        }

        public static class CropHintsAnnotation {
            private List<CropHintsBean> cropHints;

            public List<CropHintsBean> getCropHints() {
                return cropHints;
            }

            public void setCropHints(List<CropHintsBean> cropHints) {
                this.cropHints = cropHints;
            }

            public static class CropHintsBean {
                /**
                 * boundingPoly : {"vertices":[{"x":166,"y":543},{"x":606},{"x":606,"y":543},{"x":166,"y":543}]}
                 * confidence : 0.79999995
                 * importanceFraction : 0.72999996
                 */

                private BoundingPolyBean boundingPoly;
                private double confidence;
                private double importanceFraction;

                public BoundingPolyBean getBoundingPoly() {
                    return boundingPoly;
                }

                public void setBoundingPoly(BoundingPolyBean boundingPoly) {
                    this.boundingPoly = boundingPoly;
                }

                public double getConfidence() {
                    return confidence;
                }

                public void setConfidence(double confidence) {
                    this.confidence = confidence;
                }

                public double getImportanceFraction() {
                    return importanceFraction;
                }

                public void setImportanceFraction(double importanceFraction) {
                    this.importanceFraction = importanceFraction;
                }

                public static class BoundingPolyBean {
                    private List<VerticesBean> vertices;

                    public List<VerticesBean> getVertices() {
                        return vertices;
                    }

                    public void setVertices(List<VerticesBean> vertices) {
                        this.vertices = vertices;
                    }

                    public static class VerticesBean {
                        /**
                         * x : 166
                         * y : 543
                         */

                        private int x;
                        private int y;

                        public int getX() {
                            return x;
                        }

                        public void setX(int x) {
                            this.x = x;
                        }

                        public int getY() {
                            return y;
                        }

                        public void setY(int y) {
                            this.y = y;
                        }
                    }
                }
            }
        }

        public static class FullTextAnnotation {
            /**
             * pages : [{"property":{"detectedLanguages":[{"languageCode":"hu","confidence":0.52},{"languageCode":"es","confidence":0.36},{"languageCode":"eu","confidence":0.12}]},"width":799,"height":544,"blocks":[{"boundingBox":{"vertices":[{"x":236,"y":226},{"x":271,"y":230},{"x":269,"y":244},{"x":234,"y":240}]},"paragraphs":[{"boundingBox":{"vertices":[{"x":236,"y":226},{"x":271,"y":230},{"x":269,"y":244},{"x":234,"y":240}]},"words":[{"property":{"detectedLanguages":[{"languageCode":"eu"}]},"boundingBox":{"vertices":[{"x":236,"y":226},{"x":271,"y":230},{"x":269,"y":244},{"x":234,"y":240}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"eu"}]},"boundingBox":{"vertices":[{"x":236,"y":226},{"x":245,"y":227},{"x":243,"y":241},{"x":234,"y":240}]},"text":"E","confidence":0.97},{"property":{"detectedLanguages":[{"languageCode":"eu"}]},"boundingBox":{"vertices":[{"x":248,"y":228},{"x":258,"y":229},{"x":256,"y":243},{"x":246,"y":242}]},"text":"D","confidence":0.96},{"property":{"detectedLanguages":[{"languageCode":"eu"}],"detectedBreak":{"type":"LINE_BREAK"}},"boundingBox":{"vertices":[{"x":261,"y":229},{"x":271,"y":230},{"x":269,"y":244},{"x":259,"y":243}]},"text":"O","confidence":0.97}],"confidence":0.96}],"confidence":0.96}],"blockType":"TEXT","confidence":0.96},{"boundingBox":{"vertices":[{"x":492,"y":226},{"x":570,"y":187},{"x":577,"y":201},{"x":499,"y":240}]},"paragraphs":[{"boundingBox":{"vertices":[{"x":492,"y":226},{"x":570,"y":187},{"x":577,"y":201},{"x":499,"y":240}]},"words":[{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":492,"y":226},{"x":513,"y":216},{"x":520,"y":230},{"x":499,"y":240}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":492,"y":226},{"x":497,"y":223},{"x":504,"y":237},{"x":499,"y":239}]},"text":"A","confidence":0.38},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":499,"y":223},{"x":504,"y":220},{"x":511,"y":234},{"x":506,"y":236}]},"text":"R","confidence":0.74},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":507,"y":219},{"x":510,"y":218},{"x":516,"y":231},{"x":514,"y":232}]},"text":"R","confidence":0.56},{"property":{"detectedLanguages":[{"languageCode":"hu"}],"detectedBreak":{"type":"SPACE"}},"boundingBox":{"vertices":[{"x":510,"y":217},{"x":513,"y":216},{"x":519,"y":229},{"x":517,"y":230}]},"text":"A","confidence":0.22}],"confidence":0.47},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":516,"y":214},{"x":551,"y":196},{"x":558,"y":211},{"x":523,"y":228}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":516,"y":214},{"x":523,"y":210},{"x":530,"y":224},{"x":523,"y":227}]},"text":"A","confidence":0.24},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":526,"y":210},{"x":530,"y":208},{"x":536,"y":222},{"x":533,"y":223}]},"text":"M","confidence":0.1},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":529,"y":208},{"x":533,"y":206},{"x":539,"y":220},{"x":536,"y":221}]},"text":"I","confidence":0.72},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":535,"y":205},{"x":539,"y":203},{"x":546,"y":216},{"x":542,"y":218}]},"text":"R","confidence":0.94},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":541,"y":201},{"x":544,"y":200},{"x":550,"y":213},{"x":548,"y":214}]},"text":"I","confidence":0.11},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":545,"y":199},{"x":547,"y":198},{"x":554,"y":211},{"x":552,"y":212}]},"text":"A","confidence":0.77},{"property":{"detectedLanguages":[{"languageCode":"hu"}],"detectedBreak":{"type":"SPACE"}},"boundingBox":{"vertices":[{"x":549,"y":197},{"x":551,"y":196},{"x":558,"y":209},{"x":556,"y":210}]},"text":"M","confidence":0.02}],"confidence":0.41},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":553,"y":196},{"x":570,"y":187},{"x":577,"y":201},{"x":560,"y":209}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":553,"y":196},{"x":560,"y":192},{"x":567,"y":206},{"x":560,"y":209}]},"text":"A","confidence":0.09},{"property":{"detectedLanguages":[{"languageCode":"hu"}],"detectedBreak":{"type":"LINE_BREAK"}},"boundingBox":{"vertices":[{"x":563,"y":191},{"x":570,"y":187},{"x":577,"y":201},{"x":570,"y":204}]},"text":"D","confidence":0.03}],"confidence":0.06}],"confidence":0.37}],"blockType":"TEXT","confidence":0.37},{"boundingBox":{"vertices":[{"x":83,"y":221},{"x":346,"y":240},{"x":341,"y":310},{"x":78,"y":291}]},"paragraphs":[{"boundingBox":{"vertices":[{"x":83,"y":221},{"x":346,"y":240},{"x":341,"y":310},{"x":78,"y":291}]},"words":[{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":83,"y":221},{"x":103,"y":222},{"x":98,"y":291},{"x":78,"y":290}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"es"}],"detectedBreak":{"type":"SPACE"}},"boundingBox":{"vertices":[{"x":83,"y":221},{"x":103,"y":222},{"x":98,"y":291},{"x":78,"y":290}]},"text":"A","confidence":0.07}],"confidence":0.07},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":131,"y":224},{"x":346,"y":239},{"x":341,"y":309},{"x":126,"y":294}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":131,"y":224},{"x":154,"y":226},{"x":149,"y":294},{"x":126,"y":293}]},"text":"V","confidence":0.92},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":159,"y":226},{"x":182,"y":228},{"x":177,"y":296},{"x":154,"y":295}]},"text":"E","confidence":0.96},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":188,"y":229},{"x":208,"y":230},{"x":203,"y":299},{"x":183,"y":298}]},"text":"R","confidence":0.08},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":208,"y":229},{"x":231,"y":231},{"x":226,"y":299},{"x":203,"y":298}]},"text":"A","confidence":0.92},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":242,"y":232},{"x":267,"y":234},{"x":262,"y":303},{"x":237,"y":301}]},"text":"C","confidence":0.92},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":269,"y":234},{"x":294,"y":236},{"x":289,"y":305},{"x":264,"y":303}]},"text":"R","confidence":0.97},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":305,"y":236},{"x":328,"y":238},{"x":323,"y":306},{"x":300,"y":305}]},"text":"U","confidence":0.97},{"property":{"detectedLanguages":[{"languageCode":"es"}],"detectedBreak":{"type":"LINE_BREAK"}},"boundingBox":{"vertices":[{"x":329,"y":239},{"x":346,"y":240},{"x":341,"y":309},{"x":324,"y":308}]},"text":"Z","confidence":0.12}],"confidence":0.73}],"confidence":0.65}],"blockType":"TEXT","confidence":0.65}]}]
             * text : EDO
             ARRA AMIRIAM AD
             A VERACRUZ

             */

            private String text;
            private List<PagesBean> pages;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public List<PagesBean> getPages() {
                return pages;
            }

            public void setPages(List<PagesBean> pages) {
                this.pages = pages;
            }

            public static class PagesBean {
                /**
                 * property : {"detectedLanguages":[{"languageCode":"hu","confidence":0.52},{"languageCode":"es","confidence":0.36},{"languageCode":"eu","confidence":0.12}]}
                 * width : 799
                 * height : 544
                 * blocks : [{"boundingBox":{"vertices":[{"x":236,"y":226},{"x":271,"y":230},{"x":269,"y":244},{"x":234,"y":240}]},"paragraphs":[{"boundingBox":{"vertices":[{"x":236,"y":226},{"x":271,"y":230},{"x":269,"y":244},{"x":234,"y":240}]},"words":[{"property":{"detectedLanguages":[{"languageCode":"eu"}]},"boundingBox":{"vertices":[{"x":236,"y":226},{"x":271,"y":230},{"x":269,"y":244},{"x":234,"y":240}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"eu"}]},"boundingBox":{"vertices":[{"x":236,"y":226},{"x":245,"y":227},{"x":243,"y":241},{"x":234,"y":240}]},"text":"E","confidence":0.97},{"property":{"detectedLanguages":[{"languageCode":"eu"}]},"boundingBox":{"vertices":[{"x":248,"y":228},{"x":258,"y":229},{"x":256,"y":243},{"x":246,"y":242}]},"text":"D","confidence":0.96},{"property":{"detectedLanguages":[{"languageCode":"eu"}],"detectedBreak":{"type":"LINE_BREAK"}},"boundingBox":{"vertices":[{"x":261,"y":229},{"x":271,"y":230},{"x":269,"y":244},{"x":259,"y":243}]},"text":"O","confidence":0.97}],"confidence":0.96}],"confidence":0.96}],"blockType":"TEXT","confidence":0.96},{"boundingBox":{"vertices":[{"x":492,"y":226},{"x":570,"y":187},{"x":577,"y":201},{"x":499,"y":240}]},"paragraphs":[{"boundingBox":{"vertices":[{"x":492,"y":226},{"x":570,"y":187},{"x":577,"y":201},{"x":499,"y":240}]},"words":[{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":492,"y":226},{"x":513,"y":216},{"x":520,"y":230},{"x":499,"y":240}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":492,"y":226},{"x":497,"y":223},{"x":504,"y":237},{"x":499,"y":239}]},"text":"A","confidence":0.38},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":499,"y":223},{"x":504,"y":220},{"x":511,"y":234},{"x":506,"y":236}]},"text":"R","confidence":0.74},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":507,"y":219},{"x":510,"y":218},{"x":516,"y":231},{"x":514,"y":232}]},"text":"R","confidence":0.56},{"property":{"detectedLanguages":[{"languageCode":"hu"}],"detectedBreak":{"type":"SPACE"}},"boundingBox":{"vertices":[{"x":510,"y":217},{"x":513,"y":216},{"x":519,"y":229},{"x":517,"y":230}]},"text":"A","confidence":0.22}],"confidence":0.47},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":516,"y":214},{"x":551,"y":196},{"x":558,"y":211},{"x":523,"y":228}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":516,"y":214},{"x":523,"y":210},{"x":530,"y":224},{"x":523,"y":227}]},"text":"A","confidence":0.24},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":526,"y":210},{"x":530,"y":208},{"x":536,"y":222},{"x":533,"y":223}]},"text":"M","confidence":0.1},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":529,"y":208},{"x":533,"y":206},{"x":539,"y":220},{"x":536,"y":221}]},"text":"I","confidence":0.72},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":535,"y":205},{"x":539,"y":203},{"x":546,"y":216},{"x":542,"y":218}]},"text":"R","confidence":0.94},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":541,"y":201},{"x":544,"y":200},{"x":550,"y":213},{"x":548,"y":214}]},"text":"I","confidence":0.11},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":545,"y":199},{"x":547,"y":198},{"x":554,"y":211},{"x":552,"y":212}]},"text":"A","confidence":0.77},{"property":{"detectedLanguages":[{"languageCode":"hu"}],"detectedBreak":{"type":"SPACE"}},"boundingBox":{"vertices":[{"x":549,"y":197},{"x":551,"y":196},{"x":558,"y":209},{"x":556,"y":210}]},"text":"M","confidence":0.02}],"confidence":0.41},{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":553,"y":196},{"x":570,"y":187},{"x":577,"y":201},{"x":560,"y":209}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"hu"}]},"boundingBox":{"vertices":[{"x":553,"y":196},{"x":560,"y":192},{"x":567,"y":206},{"x":560,"y":209}]},"text":"A","confidence":0.09},{"property":{"detectedLanguages":[{"languageCode":"hu"}],"detectedBreak":{"type":"LINE_BREAK"}},"boundingBox":{"vertices":[{"x":563,"y":191},{"x":570,"y":187},{"x":577,"y":201},{"x":570,"y":204}]},"text":"D","confidence":0.03}],"confidence":0.06}],"confidence":0.37}],"blockType":"TEXT","confidence":0.37},{"boundingBox":{"vertices":[{"x":83,"y":221},{"x":346,"y":240},{"x":341,"y":310},{"x":78,"y":291}]},"paragraphs":[{"boundingBox":{"vertices":[{"x":83,"y":221},{"x":346,"y":240},{"x":341,"y":310},{"x":78,"y":291}]},"words":[{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":83,"y":221},{"x":103,"y":222},{"x":98,"y":291},{"x":78,"y":290}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"es"}],"detectedBreak":{"type":"SPACE"}},"boundingBox":{"vertices":[{"x":83,"y":221},{"x":103,"y":222},{"x":98,"y":291},{"x":78,"y":290}]},"text":"A","confidence":0.07}],"confidence":0.07},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":131,"y":224},{"x":346,"y":239},{"x":341,"y":309},{"x":126,"y":294}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":131,"y":224},{"x":154,"y":226},{"x":149,"y":294},{"x":126,"y":293}]},"text":"V","confidence":0.92},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":159,"y":226},{"x":182,"y":228},{"x":177,"y":296},{"x":154,"y":295}]},"text":"E","confidence":0.96},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":188,"y":229},{"x":208,"y":230},{"x":203,"y":299},{"x":183,"y":298}]},"text":"R","confidence":0.08},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":208,"y":229},{"x":231,"y":231},{"x":226,"y":299},{"x":203,"y":298}]},"text":"A","confidence":0.92},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":242,"y":232},{"x":267,"y":234},{"x":262,"y":303},{"x":237,"y":301}]},"text":"C","confidence":0.92},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":269,"y":234},{"x":294,"y":236},{"x":289,"y":305},{"x":264,"y":303}]},"text":"R","confidence":0.97},{"property":{"detectedLanguages":[{"languageCode":"es"}]},"boundingBox":{"vertices":[{"x":305,"y":236},{"x":328,"y":238},{"x":323,"y":306},{"x":300,"y":305}]},"text":"U","confidence":0.97},{"property":{"detectedLanguages":[{"languageCode":"es"}],"detectedBreak":{"type":"LINE_BREAK"}},"boundingBox":{"vertices":[{"x":329,"y":239},{"x":346,"y":240},{"x":341,"y":309},{"x":324,"y":308}]},"text":"Z","confidence":0.12}],"confidence":0.73}],"confidence":0.65}],"blockType":"TEXT","confidence":0.65}]
                 */

                private PropertyBean property;
                private int width;
                private int height;
                private List<BlocksBean> blocks;

                public PropertyBean getProperty() {
                    return property;
                }

                public void setProperty(PropertyBean property) {
                    this.property = property;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public List<BlocksBean> getBlocks() {
                    return blocks;
                }

                public void setBlocks(List<BlocksBean> blocks) {
                    this.blocks = blocks;
                }

                public static class PropertyBean {
                    private List<DetectedLanguagesBean> detectedLanguages;

                    public List<DetectedLanguagesBean> getDetectedLanguages() {
                        return detectedLanguages;
                    }

                    public void setDetectedLanguages(List<DetectedLanguagesBean> detectedLanguages) {
                        this.detectedLanguages = detectedLanguages;
                    }

                    public static class DetectedLanguagesBean {
                        /**
                         * languageCode : hu
                         * confidence : 0.52
                         */

                        private String languageCode;
                        private double confidence;

                        public String getLanguageCode() {
                            return languageCode;
                        }

                        public void setLanguageCode(String languageCode) {
                            this.languageCode = languageCode;
                        }

                        public double getConfidence() {
                            return confidence;
                        }

                        public void setConfidence(double confidence) {
                            this.confidence = confidence;
                        }
                    }
                }

                public static class BlocksBean {
                    /**
                     * boundingBox : {"vertices":[{"x":236,"y":226},{"x":271,"y":230},{"x":269,"y":244},{"x":234,"y":240}]}
                     * paragraphs : [{"boundingBox":{"vertices":[{"x":236,"y":226},{"x":271,"y":230},{"x":269,"y":244},{"x":234,"y":240}]},"words":[{"property":{"detectedLanguages":[{"languageCode":"eu"}]},"boundingBox":{"vertices":[{"x":236,"y":226},{"x":271,"y":230},{"x":269,"y":244},{"x":234,"y":240}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"eu"}]},"boundingBox":{"vertices":[{"x":236,"y":226},{"x":245,"y":227},{"x":243,"y":241},{"x":234,"y":240}]},"text":"E","confidence":0.97},{"property":{"detectedLanguages":[{"languageCode":"eu"}]},"boundingBox":{"vertices":[{"x":248,"y":228},{"x":258,"y":229},{"x":256,"y":243},{"x":246,"y":242}]},"text":"D","confidence":0.96},{"property":{"detectedLanguages":[{"languageCode":"eu"}],"detectedBreak":{"type":"LINE_BREAK"}},"boundingBox":{"vertices":[{"x":261,"y":229},{"x":271,"y":230},{"x":269,"y":244},{"x":259,"y":243}]},"text":"O","confidence":0.97}],"confidence":0.96}],"confidence":0.96}]
                     * blockType : TEXT
                     * confidence : 0.96
                     */

                    private BoundingBoxBean boundingBox;
                    private String blockType;
                    private double confidence;
                    private List<ParagraphsBean> paragraphs;

                    public BoundingBoxBean getBoundingBox() {
                        return boundingBox;
                    }

                    public void setBoundingBox(BoundingBoxBean boundingBox) {
                        this.boundingBox = boundingBox;
                    }

                    public String getBlockType() {
                        return blockType;
                    }

                    public void setBlockType(String blockType) {
                        this.blockType = blockType;
                    }

                    public double getConfidence() {
                        return confidence;
                    }

                    public void setConfidence(double confidence) {
                        this.confidence = confidence;
                    }

                    public List<ParagraphsBean> getParagraphs() {
                        return paragraphs;
                    }

                    public void setParagraphs(List<ParagraphsBean> paragraphs) {
                        this.paragraphs = paragraphs;
                    }

                    public static class BoundingBoxBean {
                        private List<VerticesBeanX> vertices;

                        public List<VerticesBeanX> getVertices() {
                            return vertices;
                        }

                        public void setVertices(List<VerticesBeanX> vertices) {
                            this.vertices = vertices;
                        }

                        public static class VerticesBeanX {
                            /**
                             * x : 236
                             * y : 226
                             */

                            private int x;
                            private int y;

                            public int getX() {
                                return x;
                            }

                            public void setX(int x) {
                                this.x = x;
                            }

                            public int getY() {
                                return y;
                            }

                            public void setY(int y) {
                                this.y = y;
                            }
                        }
                    }

                    public static class ParagraphsBean {
                        /**
                         * boundingBox : {"vertices":[{"x":236,"y":226},{"x":271,"y":230},{"x":269,"y":244},{"x":234,"y":240}]}
                         * words : [{"property":{"detectedLanguages":[{"languageCode":"eu"}]},"boundingBox":{"vertices":[{"x":236,"y":226},{"x":271,"y":230},{"x":269,"y":244},{"x":234,"y":240}]},"symbols":[{"property":{"detectedLanguages":[{"languageCode":"eu"}]},"boundingBox":{"vertices":[{"x":236,"y":226},{"x":245,"y":227},{"x":243,"y":241},{"x":234,"y":240}]},"text":"E","confidence":0.97},{"property":{"detectedLanguages":[{"languageCode":"eu"}]},"boundingBox":{"vertices":[{"x":248,"y":228},{"x":258,"y":229},{"x":256,"y":243},{"x":246,"y":242}]},"text":"D","confidence":0.96},{"property":{"detectedLanguages":[{"languageCode":"eu"}],"detectedBreak":{"type":"LINE_BREAK"}},"boundingBox":{"vertices":[{"x":261,"y":229},{"x":271,"y":230},{"x":269,"y":244},{"x":259,"y":243}]},"text":"O","confidence":0.97}],"confidence":0.96}]
                         * confidence : 0.96
                         */

                        private BoundingBoxBeanX boundingBox;
                        private double confidence;
                        private List<WordsBean> words;

                        public BoundingBoxBeanX getBoundingBox() {
                            return boundingBox;
                        }

                        public void setBoundingBox(BoundingBoxBeanX boundingBox) {
                            this.boundingBox = boundingBox;
                        }

                        public double getConfidence() {
                            return confidence;
                        }

                        public void setConfidence(double confidence) {
                            this.confidence = confidence;
                        }

                        public List<WordsBean> getWords() {
                            return words;
                        }

                        public void setWords(List<WordsBean> words) {
                            this.words = words;
                        }

                        public static class BoundingBoxBeanX {
                            private List<VerticesBeanXX> vertices;

                            public List<VerticesBeanXX> getVertices() {
                                return vertices;
                            }

                            public void setVertices(List<VerticesBeanXX> vertices) {
                                this.vertices = vertices;
                            }

                            public static class VerticesBeanXX {
                                /**
                                 * x : 236
                                 * y : 226
                                 */

                                private int x;
                                private int y;

                                public int getX() {
                                    return x;
                                }

                                public void setX(int x) {
                                    this.x = x;
                                }

                                public int getY() {
                                    return y;
                                }

                                public void setY(int y) {
                                    this.y = y;
                                }
                            }
                        }

                        public static class WordsBean {
                            /**
                             * property : {"detectedLanguages":[{"languageCode":"eu"}]}
                             * boundingBox : {"vertices":[{"x":236,"y":226},{"x":271,"y":230},{"x":269,"y":244},{"x":234,"y":240}]}
                             * symbols : [{"property":{"detectedLanguages":[{"languageCode":"eu"}]},"boundingBox":{"vertices":[{"x":236,"y":226},{"x":245,"y":227},{"x":243,"y":241},{"x":234,"y":240}]},"text":"E","confidence":0.97},{"property":{"detectedLanguages":[{"languageCode":"eu"}]},"boundingBox":{"vertices":[{"x":248,"y":228},{"x":258,"y":229},{"x":256,"y":243},{"x":246,"y":242}]},"text":"D","confidence":0.96},{"property":{"detectedLanguages":[{"languageCode":"eu"}],"detectedBreak":{"type":"LINE_BREAK"}},"boundingBox":{"vertices":[{"x":261,"y":229},{"x":271,"y":230},{"x":269,"y":244},{"x":259,"y":243}]},"text":"O","confidence":0.97}]
                             * confidence : 0.96
                             */

                            private PropertyBeanX property;
                            private BoundingBoxBeanXX boundingBox;
                            private double confidence;
                            private List<SymbolsBean> symbols;

                            public PropertyBeanX getProperty() {
                                return property;
                            }

                            public void setProperty(PropertyBeanX property) {
                                this.property = property;
                            }

                            public BoundingBoxBeanXX getBoundingBox() {
                                return boundingBox;
                            }

                            public void setBoundingBox(BoundingBoxBeanXX boundingBox) {
                                this.boundingBox = boundingBox;
                            }

                            public double getConfidence() {
                                return confidence;
                            }

                            public void setConfidence(double confidence) {
                                this.confidence = confidence;
                            }

                            public List<SymbolsBean> getSymbols() {
                                return symbols;
                            }

                            public void setSymbols(List<SymbolsBean> symbols) {
                                this.symbols = symbols;
                            }

                            public static class PropertyBeanX {
                                private List<DetectedLanguagesBeanX> detectedLanguages;

                                public List<DetectedLanguagesBeanX> getDetectedLanguages() {
                                    return detectedLanguages;
                                }

                                public void setDetectedLanguages(List<DetectedLanguagesBeanX> detectedLanguages) {
                                    this.detectedLanguages = detectedLanguages;
                                }

                                public static class DetectedLanguagesBeanX {
                                    /**
                                     * languageCode : eu
                                     */

                                    private String languageCode;

                                    public String getLanguageCode() {
                                        return languageCode;
                                    }

                                    public void setLanguageCode(String languageCode) {
                                        this.languageCode = languageCode;
                                    }
                                }
                            }

                            public static class BoundingBoxBeanXX {
                                private List<VerticesBeanXXX> vertices;

                                public List<VerticesBeanXXX> getVertices() {
                                    return vertices;
                                }

                                public void setVertices(List<VerticesBeanXXX> vertices) {
                                    this.vertices = vertices;
                                }

                                public static class VerticesBeanXXX {
                                    /**
                                     * x : 236
                                     * y : 226
                                     */

                                    private int x;
                                    private int y;

                                    public int getX() {
                                        return x;
                                    }

                                    public void setX(int x) {
                                        this.x = x;
                                    }

                                    public int getY() {
                                        return y;
                                    }

                                    public void setY(int y) {
                                        this.y = y;
                                    }
                                }
                            }

                            public static class SymbolsBean {
                                /**
                                 * property : {"detectedLanguages":[{"languageCode":"eu"}]}
                                 * boundingBox : {"vertices":[{"x":236,"y":226},{"x":245,"y":227},{"x":243,"y":241},{"x":234,"y":240}]}
                                 * text : E
                                 * confidence : 0.97
                                 */

                                private PropertyBeanXX property;
                                private BoundingBoxBeanXXX boundingBox;
                                private String text;
                                private double confidence;

                                public PropertyBeanXX getProperty() {
                                    return property;
                                }

                                public void setProperty(PropertyBeanXX property) {
                                    this.property = property;
                                }

                                public BoundingBoxBeanXXX getBoundingBox() {
                                    return boundingBox;
                                }

                                public void setBoundingBox(BoundingBoxBeanXXX boundingBox) {
                                    this.boundingBox = boundingBox;
                                }

                                public String getText() {
                                    return text;
                                }

                                public void setText(String text) {
                                    this.text = text;
                                }

                                public double getConfidence() {
                                    return confidence;
                                }

                                public void setConfidence(double confidence) {
                                    this.confidence = confidence;
                                }

                                public static class PropertyBeanXX {
                                    private List<DetectedLanguagesBeanXX> detectedLanguages;

                                    public List<DetectedLanguagesBeanXX> getDetectedLanguages() {
                                        return detectedLanguages;
                                    }

                                    public void setDetectedLanguages(List<DetectedLanguagesBeanXX> detectedLanguages) {
                                        this.detectedLanguages = detectedLanguages;
                                    }

                                    public static class DetectedLanguagesBeanXX {
                                        /**
                                         * languageCode : eu
                                         */

                                        private String languageCode;

                                        public String getLanguageCode() {
                                            return languageCode;
                                        }

                                        public void setLanguageCode(String languageCode) {
                                            this.languageCode = languageCode;
                                        }
                                    }
                                }

                                public static class BoundingBoxBeanXXX {
                                    private List<VerticesBeanXXXX> vertices;

                                    public List<VerticesBeanXXXX> getVertices() {
                                        return vertices;
                                    }

                                    public void setVertices(List<VerticesBeanXXXX> vertices) {
                                        this.vertices = vertices;
                                    }

                                    public static class VerticesBeanXXXX {
                                        /**
                                         * x : 236
                                         * y : 226
                                         */

                                        private int x;
                                        private int y;

                                        public int getX() {
                                            return x;
                                        }

                                        public void setX(int x) {
                                            this.x = x;
                                        }

                                        public int getY() {
                                            return y;
                                        }

                                        public void setY(int y) {
                                            this.y = y;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        public static class WebDetection {
            private List<WebEntities> webEntities;
            private List<FullMatchingImagesBean> fullMatchingImages;
            private List<PartialMatchingImagesBean> partialMatchingImages;
            private List<PagesWithMatchingImagesBean> pagesWithMatchingImages;
            private List<VisuallySimilarImagesBean> visuallySimilarImages;
            private List<BestGuessLabels> bestGuessLabels;

            public List<WebEntities> getWebEntities() {
                return webEntities;
            }

            public void setWebEntities(List<WebEntities> webEntities) {
                this.webEntities = webEntities;
            }

            public List<FullMatchingImagesBean> getFullMatchingImages() {
                return fullMatchingImages;
            }

            public void setFullMatchingImages(List<FullMatchingImagesBean> fullMatchingImages) {
                this.fullMatchingImages = fullMatchingImages;
            }

            public List<PartialMatchingImagesBean> getPartialMatchingImages() {
                return partialMatchingImages;
            }

            public void setPartialMatchingImages(List<PartialMatchingImagesBean> partialMatchingImages) {
                this.partialMatchingImages = partialMatchingImages;
            }

            public List<PagesWithMatchingImagesBean> getPagesWithMatchingImages() {
                return pagesWithMatchingImages;
            }

            public void setPagesWithMatchingImages(List<PagesWithMatchingImagesBean> pagesWithMatchingImages) {
                this.pagesWithMatchingImages = pagesWithMatchingImages;
            }

            public List<VisuallySimilarImagesBean> getVisuallySimilarImages() {
                return visuallySimilarImages;
            }

            public void setVisuallySimilarImages(List<VisuallySimilarImagesBean> visuallySimilarImages) {
                this.visuallySimilarImages = visuallySimilarImages;
            }

            public List<BestGuessLabels> getBestGuessLabels() {
                return bestGuessLabels;
            }

            public void setBestGuessLabels(List<BestGuessLabels> bestGuessLabels) {
                this.bestGuessLabels = bestGuessLabels;
            }

            public static class WebEntities {
                /**
                 * entityId : /m/03xkll
                 * score : 3.413219
                 * description : Jaguar C-Type
                 */

                private String entityId;
                private double score;
                private String description;

                public String getEntityId() {
                    return entityId;
                }

                public void setEntityId(String entityId) {
                    this.entityId = entityId;
                }

                public double getScore() {
                    return score;
                }

                public void setScore(double score) {
                    this.score = score;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                @Override
                public String toString() {
                    return "WebEntities{" +
                            "entityId='" + entityId + '\'' +
                            ", score=" + score +
                            ", description='" + description + '\'' +
                            '}';
                }
            }

            public static class FullMatchingImagesBean {
                /**
                 * url : https://i0.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20170205122949/d3s_6485.jpg?fit=750%2C510
                 */

                private String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class PartialMatchingImagesBean {
                /**
                 * url : https://i1.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20170205122954/d3s_6488.jpg?fit=750%2C514
                 */

                private String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class PagesWithMatchingImagesBean {
                /**
                 * url : http://www.sportscardigest.com/jaguar-c-type-xkc-029-car-profile-and-photo-gallery/
                 * pageTitle : &lt;b&gt;Jaguar C-Type&lt;/b&gt; XKC 029 - Car Profile, History and Photo Gallery
                 * fullMatchingImages : [{"url":"https://i0.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20170205122949/d3s_6485.jpg?fit=750%2C510"},{"url":"https://i1.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20170205122954/d3s_6488.jpg?fit=750%2C514"}]
                 * partialMatchingImages : [{"url":"https://i0.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20161213154657/quail-motorsports-gathering_2010-19.jpg?fit=750%2C498"}]
                 */

                private String url;
                private String pageTitle;
                private List<FullMatchingImagesBeanX> fullMatchingImages;
                private List<PartialMatchingImagesBeanX> partialMatchingImages;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getPageTitle() {
                    return pageTitle;
                }

                public void setPageTitle(String pageTitle) {
                    this.pageTitle = pageTitle;
                }

                public List<FullMatchingImagesBeanX> getFullMatchingImages() {
                    return fullMatchingImages;
                }

                public void setFullMatchingImages(List<FullMatchingImagesBeanX> fullMatchingImages) {
                    this.fullMatchingImages = fullMatchingImages;
                }

                public List<PartialMatchingImagesBeanX> getPartialMatchingImages() {
                    return partialMatchingImages;
                }

                public void setPartialMatchingImages(List<PartialMatchingImagesBeanX> partialMatchingImages) {
                    this.partialMatchingImages = partialMatchingImages;
                }

                public static class FullMatchingImagesBeanX {
                    /**
                     * url : https://i0.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20170205122949/d3s_6485.jpg?fit=750%2C510
                     */

                    private String url;

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }
                }

                public static class PartialMatchingImagesBeanX {
                    /**
                     * url : https://i0.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20161213154657/quail-motorsports-gathering_2010-19.jpg?fit=750%2C498
                     */

                    private String url;

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }
                }
            }

            public static class VisuallySimilarImagesBean {
                /**
                 * url : https://i1.wp.com/s3.amazonaws.com/scardigest/wp-content/uploads/20170205122930/d3s_6473.jpg?fit=750%2C479
                 */

                private String url;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }

            public static class BestGuessLabels {
                /**
                 * label : Jaguar C-Type
                 */

                private String label;

                public String getLabel() {
                    return label;
                }

                public void setLabel(String label) {
                    this.label = label;
                }
            }
        }

        public static class LabelAnnotations {
            /**
             * mid : /m/0k4j
             * description : car
             * score : 0.98641205
             * topicality : 0.98641205
             */

            private String mid;
            private String description;
            private double score;
            private double topicality;

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public double getTopicality() {
                return topicality;
            }

            public void setTopicality(double topicality) {
                this.topicality = topicality;
            }
        }

        public static class TextAnnotations {
            /**
             * locale : hu
             * description : EDO
             ARRA AMIRIAM AD
             A VERACRUZ

             * boundingPoly : {"vertices":[{"x":78,"y":187},{"x":577,"y":187},{"x":577,"y":309},{"x":78,"y":309}]}
             */

            private String locale;
            private String description;
            private BoundingPolyBeanX boundingPoly;

            public String getLocale() {
                return locale;
            }

            public void setLocale(String locale) {
                this.locale = locale;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public BoundingPolyBeanX getBoundingPoly() {
                return boundingPoly;
            }

            public void setBoundingPoly(BoundingPolyBeanX boundingPoly) {
                this.boundingPoly = boundingPoly;
            }

            public static class BoundingPolyBeanX {
                private List<VerticesBeanXXXXX> vertices;

                public List<VerticesBeanXXXXX> getVertices() {
                    return vertices;
                }

                public void setVertices(List<VerticesBeanXXXXX> vertices) {
                    this.vertices = vertices;
                }

                public static class VerticesBeanXXXXX {
                    /**
                     * x : 78
                     * y : 187
                     */

                    private int x;
                    private int y;

                    public int getX() {
                        return x;
                    }

                    public void setX(int x) {
                        this.x = x;
                    }

                    public int getY() {
                        return y;
                    }

                    public void setY(int y) {
                        this.y = y;
                    }
                }
            }
        }
    }
}
