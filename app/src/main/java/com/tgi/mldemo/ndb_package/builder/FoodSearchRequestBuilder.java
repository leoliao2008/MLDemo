package com.tgi.mldemo.ndb_package.builder;

import com.tgi.mldemo.ndb_package.Static;
import com.tgi.mldemo.ndb_package.base.AbsRequestBuilder;
import com.tgi.mldemo.ndb_package.enums.DataSource;
import com.tgi.mldemo.ndb_package.request.FoodSearchRequest;
import com.tgi.mldemo.ndb_package.enums.SortType;

/**
 * <a href="https://ndb.nal.usda.gov/ndb/doc/apilist/API-SEARCH.md">Click Here for reference.</a>
 */
public class FoodSearchRequestBuilder extends AbsRequestBuilder<FoodSearchRequest> {


    @Override
    protected void initRequestParams() {
        //        Name	    Required	Default	Description
        //        api_key	y	          n/a	Must be a data.gov registered API key
        //        q	        n	          ""	Search terms
        //        ds	    n	          ""	Data source. Must be either 'Branded Food Products' or 'Standard Reference'
        //        fg	    n	          ""	Food group ID
        //        sort	    n	           r	Sort the results by food name (n) or by search relevance (r)
        //        max	    n	          50	maximum rows to return
        //        offset	n	          0	    beginning row in the result set to begin
        //        format1	n	          JSON	results format: json or xml
        mParams =new String[]{
                "",//Search terms
                "Standard Reference",//Data source
                "",//Food group ID
                "r",//Sort the results by food name (n) or by search relevance (r)
                "25",//maximum rows to return
                "0",//beginning row in the result set to begin
                "JSON"//results format: json or xml
        };

    }

    public FoodSearchRequest build(){
//        Name	    Required	Default	Description
//        api_key	y	          n/a	Must be a data.gov registered API key
//        q	        n	          ""	Search terms
//        ds	    n	          ""	Data source. Must be either 'Branded Food Products' or 'Standard Reference'
//        fg	    n	          ""	Food group ID
//        sort	    n	           r	Sort the results by food name (n) or by search relevance (r)
//        max	    n	          50	maximum rows to return
//        offset	n	          0	    beginning row in the result set to begin
//        format1	n	          JSON	results format: json or xml
//        private String[] mRequestParams=new String[]{
//                "",//Search terms 0
//                "Standard Reference",//Data source 1
//                "",//Food group ID 2
//                "r",//Sort the results by food name (n) or by search relevance (r) 3
//                "10",//maximum rows to return 4
//                "0",//beginning row in the result set to begin 5
//                "JSON"//results format: json or xml 6
//        };
        StringBuilder sb=new StringBuilder();
        sb.append(Static.END_POINT).append("/search/?").append("api_key=").append(Static.API_KEY).append('&');
        sb.append("q=").append(mParams[0]).append('&');
        sb.append("ds=").append(mParams[1]).append('&');
        sb.append("fg=").append(mParams[2]).append('&');
        sb.append("sort=").append(mParams[3]).append('&');
        sb.append("max=").append(mParams[4]).append('&');
        sb.append("offset=").append(mParams[5]).append('&');
        sb.append("format1=").append(mParams[6]);
        return new FoodSearchRequest(sb.toString());
    }

    public FoodSearchRequestBuilder searchTerm(String keyWord){
        mParams[0]=keyWord;
        return this;
    }

    public FoodSearchRequestBuilder dataSource(DataSource ds){
        switch (ds){
            case BRANDED_FOOD_PRODUCTS:
                mParams[1]="Branded Food Products";
                break;
            default:
                mParams[1]="Standard Reference";
                break;
        }
        return this;
    }

    public FoodSearchRequestBuilder groupId(String groupId){
        mParams[2]=groupId;
        return this;
    }

    public FoodSearchRequestBuilder sortBy(SortType sortType){
        switch (sortType){
            case BY_FOOD_NAME:
                mParams[3]="n";
                break;
            default:
                mParams[3]="r";
                break;
        }
        return this;
    }

    public FoodSearchRequestBuilder maxResult(int max){
        max=Math.min(Math.max(1,max),50);//set the result limit to 1-50
        mParams[4]=String.valueOf(max);
        return this;
    }

}
