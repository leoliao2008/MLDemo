package com.tgi.mldemo.ndb_package.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

/**
 * Details of this class can be found via:
 * https://ndb.nal.usda.gov/ndb/doc/apilist/API-FOOD-REPORTV2.md
 */
public class FoodReportResponses implements Parcelable {

    @SerializedName("count")
    private int mFoodCnt;
    @SerializedName("notfound")
    private int mNoFoundCnt;
    @SerializedName("api")
    private double mAPI;
    @SerializedName("foods")
    private List<SearchResult> mSearchResults;

    protected FoodReportResponses(Parcel in) {
        mFoodCnt = in.readInt();
        mNoFoundCnt = in.readInt();
        mAPI = in.readDouble();
        mSearchResults = in.createTypedArrayList(SearchResult.CREATOR);
    }

    public static final Creator<FoodReportResponses> CREATOR = new Creator<FoodReportResponses>() {
        @Override
        public FoodReportResponses createFromParcel(Parcel in) {
            return new FoodReportResponses(in);
        }

        @Override
        public FoodReportResponses[] newArray(int size) {
            return new FoodReportResponses[size];
        }
    };

    public int getFoodCnt() {
        return mFoodCnt;
    }

    public void setFoodCnt(int mFoodCnt) {
        this.mFoodCnt = mFoodCnt;
    }

    public int getNoFoundCnt() {
        return mNoFoundCnt;
    }

    public void setNoFoundCnt(int mNoFoundCnt) {
        this.mNoFoundCnt = mNoFoundCnt;
    }

    public double getAPI() {
        return mAPI;
    }

    public void setAPI(double mAPI) {
        this.mAPI = mAPI;
    }

    public List<SearchResult> getSearchResults() {
        return mSearchResults;
    }

    public void setSearchResults(List<SearchResult> searchResults) {
        this.mSearchResults = searchResults;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mFoodCnt);
        dest.writeInt(mNoFoundCnt);
        dest.writeDouble(mAPI);
        dest.writeTypedList(mSearchResults);
    }

    public static class SearchResult implements Parcelable {

        @SerializedName("food")
        private FoodReport mFoodReport;
        @SerializedName("error")
        private String mError;

        protected SearchResult(Parcel in) {
            mFoodReport = in.readParcelable(FoodReport.class.getClassLoader());
            mError = in.readString();
        }

        public static final Creator<SearchResult> CREATOR = new Creator<SearchResult>() {
            @Override
            public SearchResult createFromParcel(Parcel in) {
                return new SearchResult(in);
            }

            @Override
            public SearchResult[] newArray(int size) {
                return new SearchResult[size];
            }
        };

        public FoodReport getFoodReport() {
            return mFoodReport;
        }

        public void setFoodReport(FoodReport mFoodReport) {
            this.mFoodReport = mFoodReport;
        }

        public String getError() {
            return mError;
        }

        public void setError(String mError) {
            this.mError = mError;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(mFoodReport, flags);
            dest.writeString(mError);
        }

        public static class FoodReport implements Parcelable{

            @SerializedName("sr")
            private String mReleaseVersion;
            @SerializedName("type")
            private String mReportType;
            @SerializedName("desc")
            private FoodMatadataDesc mDesc;
            @SerializedName("nutrients")
            private List<Nutrient> mNutrients;
            private String mThumbNailPath;
            private long mDate;
            private String mSearchItem;


            public FoodReport() {
            }

            protected FoodReport(Parcel in) {
                mReleaseVersion = in.readString();
                mReportType = in.readString();
                mDesc = in.readParcelable(FoodMatadataDesc.class.getClassLoader());
                mNutrients = in.createTypedArrayList(Nutrient.CREATOR);
                mThumbNailPath=in.readString();
                mDate=in.readLong();
                mSearchItem =in.readString();
            }

            public String getSearchItem() {
                return mSearchItem;
            }

            public void setSearchItem(String searchItem) {
                mSearchItem = searchItem;
            }

            public long getDate() {
                return mDate;
            }

            public void setDate(long date) {
                mDate = date;
            }

            public String getThumbNailPath() {
                return mThumbNailPath;
            }

            public void setThumbNailPath(String path) {
                mThumbNailPath = path;
            }

            public static final Creator<FoodReport> CREATOR = new Creator<FoodReport>() {
                @Override
                public FoodReport createFromParcel(Parcel in) {
                    return new FoodReport(in);
                }

                @Override
                public FoodReport[] newArray(int size) {
                    return new FoodReport[size];
                }
            };

            public String getReleaseVersion() {
                return mReleaseVersion;
            }

            public void setReleaseVersion(String mReleaseVersion) {
                this.mReleaseVersion = mReleaseVersion;
            }

            public String getReportType() {
                return mReportType;
            }

            public void setReportType(String mReportType) {
                this.mReportType = mReportType;
            }

            public FoodMatadataDesc getDesc() {
                return mDesc;
            }

            public void setDesc(FoodMatadataDesc mDesc) {
                this.mDesc = mDesc;
            }

            public List<Nutrient> getNutrients() {
                return mNutrients;
            }

            public void setNutrients(List<Nutrient> mNutrients) {
                this.mNutrients = mNutrients;
            }

            private String getValueById(int id) {
                int index = mNutrients.indexOf(new Nutrient(id));
                if(index!=-1){
                    Nutrient nutrient = mNutrients.get(index);
                    return nutrient.getValue()+nutrient.getUnit();
                }
                return null;
            }

            public String getWater() {
                return getValueById(255);
            }

            public String getCalories() {
                return getValueById(208);
            }

            public String getProtein() {
                return getValueById(203);
            }

            public String getFat() {
                return getValueById(204);
            }

            public String getCarbohydrate() {
                return getValueById(205);
            }

            public String getFiber() {
                return getValueById(291);
            }

            public String getSugar() {
                return getValueById(269);
            }

            public String getCalcium() {
                return getValueById(301);
            }

            public String getIron() {
                return getValueById(303);
            }

            public String getMagnesium() {
                return getValueById(304);
            }

            public String getPhosphorus() {
                return getValueById(305);
            }

            public String getPotassium() {
                return getValueById(306);
            }

            public String getSodium() {
                return getValueById(307);
            }

            public String getZinc() {
                return getValueById(309);
            }

            public String getThiamin() {
                return getValueById(404);
            }

            public String getNiacin() {
                return getValueById(406);
            }

            public String getVitaminB6() {
                return getValueById(415);
            }

            public String getVitaminB12() {
                return getValueById(418);
            }

            public String getFolate() {
                return getValueById(435);
            }

            public String getVitaminA_RAE() {
                return getValueById(320);
            }

            public String getVitaminA_IU() {
                return getValueById(318);
            }

            public String getVitaminE() {
                return getValueById(323);
            }

            public String getVitaminK() {
                return getValueById(430);
            }

            public String getVitaminC() {
                return getValueById(401);
            }

            public String getRiboflavin() {
                return getValueById(405);
            }

            public String getSatureatedFat() {
                return getValueById(606);
            }

            public String getMonounsaturatedFat() {
                return getValueById(645);
            }

            public String getPolyunsaturatedFat() {
                return getValueById(646);
            }

            public String getCholesterol() {
                return getValueById(601);
            }

            public String getCafferine() {
                return getValueById(262);
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(mReleaseVersion);
                dest.writeString(mReportType);
                dest.writeParcelable(mDesc, flags);
                dest.writeTypedList(mNutrients);
                dest.writeString(mThumbNailPath);
                dest.writeLong(mDate);
                dest.writeString(mSearchItem);
            }


            public static class FoodMatadataDesc implements Parcelable {

                @SerializedName("ndbno")
                private String mNdbNo;
                @SerializedName("name")
                private String mFoodName;
                @SerializedName("ds")
                private String mDataSource;
                @SerializedName("manu")
                private String mManufacturer;
                @SerializedName("ru")
                private String mUnit;

                public FoodMatadataDesc() {
                }

                protected FoodMatadataDesc(Parcel in) {
                    mNdbNo = in.readString();
                    mFoodName = in.readString();
                    mDataSource = in.readString();
                    mManufacturer = in.readString();
                    mUnit = in.readString();
                }

                public static final Creator<FoodMatadataDesc> CREATOR = new Creator<FoodMatadataDesc>() {
                    @Override
                    public FoodMatadataDesc createFromParcel(Parcel in) {
                        return new FoodMatadataDesc(in);
                    }

                    @Override
                    public FoodMatadataDesc[] newArray(int size) {
                        return new FoodMatadataDesc[size];
                    }
                };

                public String getNdbNo() {
                    return mNdbNo;
                }

                public void setNdbNo(String mFoodId) {
                    this.mNdbNo = mFoodId;
                }

                public String getFoodName() {
                    return mFoodName;
                }

                public void setFoodName(String mFoodName) {
                    this.mFoodName = mFoodName;
                }

                public String getDataSource() {
                    return mDataSource;
                }

                public void setDataSource(String mDataSource) {
                    this.mDataSource = mDataSource;
                }

                public String getManufacturer() {
                    return mManufacturer;
                }

                public void setManufacturer(String mManufacturer) {
                    this.mManufacturer = mManufacturer;
                }

                public String getUnit() {
                    return mUnit;
                }

                public void setUnit(String mUnit) {
                    this.mUnit = mUnit;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(mNdbNo);
                    dest.writeString(mFoodName);
                    dest.writeString(mDataSource);
                    dest.writeString(mManufacturer);
                    dest.writeString(mUnit);
                }
            }

            public static class Nutrient implements Parcelable {
                @SerializedName("nutrient_id")
                private String mNutrientId;
                @SerializedName("name")
                private String mNutrientName;
                @SerializedName("derivation")
                private String mDerivationInfo;
                @SerializedName("group")
                private String mGroup;
                @SerializedName("unit")
                private String mUnit;
                @SerializedName("value")
                private String mValue;
                @SerializedName("measures")
                private List<Measures> mMeasures;

                public Nutrient() {
                }

                public Nutrient(int nutrientId) {
                    mNutrientId = String.valueOf(nutrientId);
                }

                protected Nutrient(Parcel in) {
                    mNutrientId = in.readString();
                    mNutrientName = in.readString();
                    mDerivationInfo = in.readString();
                    mGroup = in.readString();
                    mUnit = in.readString();
                    mValue = in.readString();
                    mMeasures = in.createTypedArrayList(Measures.CREATOR);
                }

                public static final Creator<Nutrient> CREATOR = new Creator<Nutrient>() {
                    @Override
                    public Nutrient createFromParcel(Parcel in) {
                        return new Nutrient(in);
                    }

                    @Override
                    public Nutrient[] newArray(int size) {
                        return new Nutrient[size];
                    }
                };

                public String getNutrientId() {
                    return mNutrientId;
                }

                public void setNutrientId(String mNutrientId) {
                    this.mNutrientId = mNutrientId;
                }

                public String getNutrientName() {
                    return mNutrientName;
                }

                public void setNutrientName(String mNutrinetName) {
                    this.mNutrientName = mNutrinetName;
                }

                public String getDerivationInfo() {
                    return mDerivationInfo;
                }

                public void setDerivationInfo(String mDerivationInfo) {
                    this.mDerivationInfo = mDerivationInfo;
                }

                public String getGroup() {
                    return mGroup;
                }

                public void setGroup(String mGroup) {
                    this.mGroup = mGroup;
                }

                public String getUnit() {
                    return mUnit;
                }

                public void setUnit(String mUnit) {
                    this.mUnit = mUnit;
                }

                public String getValue() {
                    return mValue;
                }

                public void setValue(String mValue) {
                    this.mValue = mValue;

                }

                public List<Measures> getMeasures() {
                    return mMeasures;
                }

                public void setMeasures(List<Measures> mMeasures) {
                    this.mMeasures = mMeasures;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public boolean equals(Object o) {
                    if (this == o)
                        return true;
                    if (o == null || getClass() != o.getClass())
                        return false;
                    Nutrient nutrient = (Nutrient) o;
                    return Objects.equals(mNutrientId, nutrient.mNutrientId);
                }

                @Override
                public int hashCode() {
                    return Objects.hash(mNutrientId);
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(mNutrientId);
                    dest.writeString(mNutrientName);
                    dest.writeString(mDerivationInfo);
                    dest.writeString(mGroup);
                    dest.writeString(mUnit);
                    dest.writeString(mValue);
                    dest.writeTypedList(mMeasures);
                }

                public static class Measures implements Parcelable {
                    @SerializedName("label")
                    private String mName;
                    @SerializedName("eqv")
                    private double mPerEnunitValue;
                    @SerializedName("eunit")
                    private String mEunit;
                    @SerializedName("qty")
                    private double mQuantity;
                    @SerializedName("value")
                    private String mValuePerQuantity;

                    protected Measures(Parcel in) {
                        mName = in.readString();
                        mPerEnunitValue = in.readDouble();
                        mEunit = in.readString();
                        mQuantity = in.readDouble();
                        mValuePerQuantity = in.readString();
                    }

                    public static final Creator<Measures> CREATOR = new Creator<Measures>() {
                        @Override
                        public Measures createFromParcel(Parcel in) {
                            return new Measures(in);
                        }

                        @Override
                        public Measures[] newArray(int size) {
                            return new Measures[size];
                        }
                    };

                    public String getName() {
                        return mName;
                    }

                    public void setName(String mName) {
                        this.mName = mName;
                    }

                    public double getPerEnunitValue() {
                        return mPerEnunitValue;
                    }

                    public void setPerEnunitValue(double mPerEnunitValue) {
                        this.mPerEnunitValue = mPerEnunitValue;
                    }

                    public String getEunit() {
                        return mEunit;
                    }

                    public void setEunit(String mEunit) {
                        this.mEunit = mEunit;
                    }

                    public double getQuantity() {
                        return mQuantity;
                    }

                    public void setQuantity(double mQuantity) {
                        this.mQuantity = mQuantity;
                    }

                    public String getValuePerQuantity() {
                        return mValuePerQuantity;
                    }

                    public void setValuePerQuantity(String mValuePerQuantity) {
                        this.mValuePerQuantity = mValuePerQuantity;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(mName);
                        dest.writeDouble(mPerEnunitValue);
                        dest.writeString(mEunit);
                        dest.writeDouble(mQuantity);
                        dest.writeString(mValuePerQuantity);
                    }
                }
            }
        }
    }
}
