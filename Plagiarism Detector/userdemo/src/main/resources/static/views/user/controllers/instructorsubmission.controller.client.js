(function(){
	angular
	.module("UserDemo")
	.controller("instructorSubmissionController", instructorSubmissionController);

	function instructorSubmissionController(UserService,InstructorService,SubmissionService,$location, $routeParams, $scope, $sce) {

    var vm = this;
    $scope.submissionList = [];
    $scope.assignmentId = $routeParams.aid;

    function init(){

        SubmissionService
        .getSubmissionByAssignmentId($scope.assignmentId)
        .then(function (resp) {

          console.log(resp.data)

          if(resp.data.length > 0){

          for(var i=0; i<resp.data.length; i++){
            $scope.submissionList[i] = {}
            $scope.submissionList[i].student = resp.data[i].student.emailId;
            $scope.submissionList[i].id = resp.data[i].submissionId;
            $scope.submissionList[i].fileLink = resp.data[i].fileLink;
          }


          }

        });

    }

    init();

  }

})();
