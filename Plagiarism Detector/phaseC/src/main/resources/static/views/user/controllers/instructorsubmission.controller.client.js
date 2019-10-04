(function(){
	angular
	.module("UserDemo")
	.controller("instructorSubmissionController", instructorSubmissionController);

	function instructorSubmissionController(UserService,InstructorService,SubmissionService,MenuService,$location, $routeParams, $scope, $sce, $window) {

    var vm = this;
    vm.logout = logout;
    $scope.submissionList = [];
    $scope.assignmentId = $routeParams.aid;
    $scope.courselist = [];
    $scope.current_course = {};
    $scope.current_assignment = {};
    $scope.current_user = $window.localStorage.getItem("sher_user_id");
	$scope.current_role = $window.localStorage.getItem("sher_user_role");
	
	if(!$scope.current_user){
		console.log("IF")
		window.location.replace("#/");
	} else {
		console.log("ELse")
		if ($scope.current_role != "instructor"){
			window.location.replace("#/");
		} else {
			init();					
		}
	}
	
    function init(){
    	
    	var menu = MenuService.getMenu();
		if (menu){
			console.log("HAVE MENU")
			$scope.courselist = menu;
		} else {
			console.log("DONT HAVE MENU")
			getAllCoursesForUserID($scope.current_user);				
		}
		
        SubmissionService
        .getSubmissionByAssignmentId($scope.assignmentId)
        .then(function (resp) {

          console.log(resp.data)
          $scope.current_course = resp.data[0].assignment.course;
          $scope.current_assignment = resp.data[0].assignment;
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
    

	function getAllCoursesForUserID(userId){
		InstructorService
		.getAllCourses(userId)
		.then(function (response){
			var courses=response.data;
			$scope.courselist = response.data;
			MenuService.setMenu($scope.courselist);
		}
		,function (error){

		})
	}
	
	function logout(){
		var auth2 = null;
		var googleUser = null;

		gapi.load('auth2', function() {//load in the auth2 api's, without it gapi.auth2 will be undefined
			alert("in Logout");
			gapi.auth2.init(
					{
						client_id: '360591443014-895c59hru7tn8ppdo0fdjh0kplggvn4p.apps.googleusercontent.com'
					}
			);

			auth2 = gapi.auth2.getAuthInstance();
			googleUser = auth2.currentUser.get();
			console.log(googleUser.isSignedIn() + "latest");
			if(googleUser.isSignedIn()){
				auth2.signOut().then(function(){
					auth2.disconnect();
					auth2.currentUser.get().reloadAuthResponse();
					console.log("signed out");
				});
			}
		});
		var currentUrl = $window.location.href;

		$window.location.href="https://appengine.google.com/_ah/logout?continue=https://neuidmsso.neu.edu/logout.html?continue="+currentUrl;
		UserService.logout();
	}


  }

})();
