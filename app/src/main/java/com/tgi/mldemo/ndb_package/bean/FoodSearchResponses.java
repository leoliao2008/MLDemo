package com.tgi.mldemo.ndb_package.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * <a href="https://ndb.nal.usda.gov/ndb/doc/apilist/API-SEARCH.md">Click Here for reference.</a>
 */
public class FoodSearchResponses implements Parcelable{

    @SerializedName("list")
    private Response mResponse;


    protected FoodSearchResponses(Parcel in) {
        mResponse = in.readParcelable(Response.class.getClassLoader());
    }

    public static final Creator<FoodSearchResponses> CREATOR = new Creator<FoodSearchResponses>() {
        @Override
        public FoodSearchResponses createFromParcel(Parcel in) {
            return new FoodSearchResponses(in);
        }

        @Override
        public FoodSearchResponses[] newArray(int size) {
            return new FoodSearchResponses[size];
        }
    };

    public Response getResponse() {
        return mResponse;
    }

    public void setResponse(Response response) {
        this.mResponse = response;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mResponse, flags);
    }


    public static class Response implements Parcelable {

        @SerializedName("q")
        private String mSearchItem;
        @SerializedName("sr")
        private String mReleaseStandard;
        @SerializedName("ds")
        private String mDataSource;
        @SerializedName("start")
        private int mBeginItem;
        @SerializedName("end")
        private int mEndItem;
        @SerializedName("total")
        private int mTotalCnt;
        @SerializedName("group")
        private String mGroupFilter;
        @SerializedName("sort")
        private String mSortOder;
        @SerializedName("item")
        private List<Item> mItems;


        protected Response(Parcel in) {
            mSearchItem = in.readString();
            mReleaseStandard = in.readString();
            mDataSource = in.readString();
            mBeginItem = in.readInt();
            mEndItem = in.readInt();
            mTotalCnt = in.readInt();
            mGroupFilter = in.readString();
            mSortOder = in.readString();
            mItems = in.createTypedArrayList(Item.CREATOR);
        }

        public static final Creator<Response> CREATOR = new Creator<Response>() {
            @Override
            public Response createFromParcel(Parcel in) {
                return new Response(in);
            }

            @Override
            public Response[] newArray(int size) {
                return new Response[size];
            }
        };

        public String getSearchItem() {
            return mSearchItem;
        }

        public void setSearchItem(String searchItem) {
            this.mSearchItem = searchItem;
        }

        public String getReleaseStandard() {
            return mReleaseStandard;
        }

        public void setReleaseStandard(String releaseStandard) {
            this.mReleaseStandard = releaseStandard;
        }

        public String getDataSource() {
            return mDataSource;
        }

        public void setDataSource(String dataSource) {
            this.mDataSource = dataSource;
        }

        public int getBeginItem() {
            return mBeginItem;
        }

        public void setBeginItem(int beginItem) {
            this.mBeginItem = beginItem;
        }

        public int getEndItem() {
            return mEndItem;
        }

        public void setEndItem(int endItem) {
            this.mEndItem = endItem;
        }

        public int getTotalCnt() {
            return mTotalCnt;
        }

        public void setTotalCnt(int totalCnt) {
            this.mTotalCnt = totalCnt;
        }

        public String getGroupFilter() {
            return mGroupFilter;
        }

        public void setGroupFilter(String groupFilter) {
            this.mGroupFilter = groupFilter;
        }

        public String getSortOder() {
            return mSortOder;
        }

        public void setSortOder(String sortOder) {
            this.mSortOder = sortOder;
        }

        public List<Item> getItems() {
            return mItems;
        }

        public void setItems(List<Item> items) {
            this.mItems = items;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mSearchItem);
            dest.writeString(mReleaseStandard);
            dest.writeString(mDataSource);
            dest.writeInt(mBeginItem);
            dest.writeInt(mEndItem);
            dest.writeInt(mTotalCnt);
            dest.writeString(mGroupFilter);
            dest.writeString(mSortOder);
            dest.writeTypedList(mItems);
        }

        public static class Item implements Parcelable {

            @SerializedName("offset")
            private int mOffset;
            @SerializedName("group")
            private String mGroup;
            @SerializedName("name")
            private String mName;
            @SerializedName("ndbno")
            private String mNdbNo;
            @SerializedName("ds")
            private String mDataSource;
            @SerializedName("manu")
            private String mManufacturer;

            protected Item(Parcel in) {
                mOffset = in.readInt();
                mGroup = in.readString();
                mName = in.readString();
                mNdbNo = in.readString();
                mDataSource = in.readString();
                mManufacturer = in.readString();
            }

            public static final Creator<Item> CREATOR = new Creator<Item>() {
                @Override
                public Item createFromParcel(Parcel in) {
                    return new Item(in);
                }

                @Override
                public Item[] newArray(int size) {
                    return new Item[size];
                }
            };

            public int getOffset() {
                return mOffset;
            }

            public void setOffset(int offset) {
                this.mOffset = offset;
            }

            public String getGroup() {
                return mGroup;
            }

            public void setGroup(String group) {
                this.mGroup = group;
            }

            public String getName() {
                return mName;
            }

            public void setName(String name) {
                this.mName = name;
            }

            public String getNdbNo() {
                return mNdbNo;
            }

            public void setNdbNo(String ndbNo) {
                this.mNdbNo = ndbNo;
            }

            public String getDataSource() {
                return mDataSource;
            }

            public void setDataSource(String dataSource) {
                this.mDataSource = dataSource;
            }

            public String getManufacturer() {
                return mManufacturer;
            }

            public void setManufacturer(String manufacturer) {
                this.mManufacturer = manufacturer;
            }

            @Override
            public String toString() {
                return "Item{" +
                        "mOffset=" + mOffset +
                        ", mGroup='" + mGroup + '\'' +
                        ", mName='" + mName + '\'' +
                        ", mNdbNo='" + mNdbNo + '\'' +
                        ", mDataSource='" + mDataSource + '\'' +
                        ", mManufacturer='" + mManufacturer + '\'' +
                        '}';
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(mOffset);
                dest.writeString(mGroup);
                dest.writeString(mName);
                dest.writeString(mNdbNo);
                dest.writeString(mDataSource);
                dest.writeString(mManufacturer);
            }
        }

        @Override
        public String toString() {
            return "Response{" +
                    "mSearchItem='" + mSearchItem + '\'' +
                    ", mReleaseStandard='" + mReleaseStandard + '\'' +
                    ", mDataSource='" + mDataSource + '\'' +
                    ", mBeginItem=" + mBeginItem +
                    ", mEndItem=" + mEndItem +
                    ", mTotalCnt=" + mTotalCnt +
                    ", mGroupFilter='" + mGroupFilter + '\'' +
                    ", mSortOder='" + mSortOder + '\'' +
                    ", mItems=" + mItems +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "FoodSearchResponses{" +
                "mResponse=" + mResponse +
                '}';
    }
}
