package com.tgi.mldemo.bean;

import java.util.List;

public class IdentifyImageResponseBean {
    private List<ResponsesBean> responses;

    public List<ResponsesBean> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponsesBean> responses) {
        this.responses = responses;
    }

    public static class ResponsesBean {
        private List<LabelAnnotationsBean> labelAnnotations;

        public List<LabelAnnotationsBean> getLabelAnnotations() {
            return labelAnnotations;
        }

        public void setLabelAnnotations(List<LabelAnnotationsBean> labelAnnotations) {
            this.labelAnnotations = labelAnnotations;
        }

        public static class LabelAnnotationsBean {
            /**
             * mid : /m/0bt9lr
             * description : dog
             * score : 0.97346616
             */

            private String mid;
            private String description;
            private double score;

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

            @Override
            public String toString() {
                return "LabelAnnotationsBean{" +
                        "mid='" + mid + '\'' +
                        ", description='" + description + '\'' +
                        ", score=" + score +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "IdentifyImageResponseBean{" +
                "responses=" + responses +
                '}';
    }
}
