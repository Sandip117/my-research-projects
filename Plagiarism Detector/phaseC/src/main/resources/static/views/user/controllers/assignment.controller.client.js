(function(){
	angular
	.module("UserDemo")
	.controller("assignmentController", assignmentController);

	function assignmentController(UserService,InstructorService,SemesterService,MenuService,SubmissionService,$location, $routeParams, $scope, $window) {
    var vm = this;
    vm.logout = logout;
    $scope.assignId = $routeParams.aid;
    $scope.courseId = $routeParams.cid;
    $scope.currSem = 4;
    $scope.assignment = [];
    $scope.user;
    $scope.allSubmissions = [];
    $scope.userSubmissions = [];
		$scope.menuItems = [];
		$scope.studentCourseList = [];
		$scope.courseForSemester = [];

    vm.fileUpload = fileUpload;
    vm.clickSubmit = clickSubmit;
    
    $scope.userId = $window.localStorage.getItem("sher_user_id");
	$scope.user_role = $window.localStorage.getItem("sher_user_role");
	
	if(!$scope.userId){
		window.location.replace("#/");
	} else {
		if($scope.user_role != "student"){
			window.location.replace("#/");
		} else {
		    init();
		}
	}
    
    function init(){
      getAssignment();
      getUserById();
      getSubmissionsByAssignment();
			getMenu();
    }


		function getMenu(){
			var m = MenuService.getMenu();
			if (m == undefined){

				SemesterService
				.getAllCourses($scope.userId)
				.then(function(response){
					$scope.studentCourseList = response.data
					console.log($scope.studentCourseList)
					InstructorService
									.getSemesterById($scope.currSem)
									.then(function(resp) {
											var selectedSemesterId = resp.data.semesterId;
											for (var i=0;i<$scope.studentCourseList.length;i++){
													var semId = $scope.studentCourseList[i].course.semester.semesterId;
													if(semId == selectedSemesterId)
															$scope.courseForSemester.push($scope.studentCourseList[i])
											}
											console.log($scope.courseForSemester)

											MenuService.setMenu($scope.courseForSemester);

									});
				})
			} else {
				$scope.courseForSemester = m;
			}
		}

    function getAssignment(){
      InstructorService
        .getAssignmentById($scope.assignId)
        .then(function(response){
          $scope.assignment = response.data;
          console.log($scope.assignment)

        })
    }

    function fileUpload(files){
      console.log("UPLOAD")
      console.log(files)
    }

    function clickSubmit(){
      var fL = $scope.currSem + "/" + $scope.courseId + "/" + $scope.assignId + "/" + $scope.userId + "/" +$scope.userSubmissions.length;
      var fN = $scope.userId;
      var submit = {fileName: fN, fileLink: fL, assignment: $scope.assignment, student: $scope.user};
      SubmissionService
      .putSubmission(submit)
      .then(function(resp){
        alert(resp.data);
      })
    }

    function getUserById(){
      UserService
      .findUserById($scope.userId)
      .then(function(resp){
        $scope.user = resp.data;
      })
    }

    function getSubmissionsByAssignment(){
      SubmissionService
      .getSubmissionByAssignmentId($scope.assignId)
      .then(function(resp){
        $scope.allSubmissions = resp.data;
        console.log("all submissions");
        console.log($scope.allSubmissions)

        for(var j=0; j<$scope.allSubmissions.length; j++){
          if($scope.allSubmissions[j].student.id == $scope.userId){
            $scope.userSubmissions.push($scope.allSubmissions[j])
          }
        }

        console.log("USER SUBU")
        console.log($scope.userSubmissions)
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
