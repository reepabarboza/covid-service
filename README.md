# Covid Application

### Covid Service to fetch covid data statistics
- **/healthCheck**
    Checks the application health
      
- **/covid/fetchCovidData?{country}&{pageNumber}&{pageSize}**
    This GET method will take optional input parameters country, pageNumber and pageSize. If parameters are not 
    specified then this method will return covid statistics from Rapid API for entire world. The pageNumber 
    parameter is used to specify the page to be displayed and  pageSize parameter specifies the number of records 
    in the given page.