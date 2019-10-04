(function(){
	angular
	.module("UserDemo")
	.factory("SubmissionService",SubmissionService);


	function SubmissionService($http) {
		var api = {
				"putSubmission":putSubmission,
        "getSubmissionByAssignmentId":getSubmissionByAssignmentId,
				"getReportByAssignId":getReportByAssignId,
				"getReportByMossId":getReportByMossId
    };
		return api;

    function putSubmission(subObj){
      return $http.post("/api/submission/", subObj);
    }

    function getSubmissionByAssignmentId(aid){
      return $http.get("/api/submission?assignId="+aid);
    }

		function getReportByAssignId(aid){
			return $http.get("/api/allReports?assignId="+aid);
		}

		function getReportByMossId(mid){
			return $http.get("/api/report?mossId="+mid);
		}

	}

})();
