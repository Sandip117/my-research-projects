(function(){
	angular
	.module("UserDemo")
	.controller("studentController", studentController);

	function studentController(UserService,InstructorService,SemesterService,MenuService,$location, $rootScope, $routeParams, $scope, $window) {
		var vm = this;
		vm.userId = $window.localStorage.getItem("sher_user_id");
		vm.logout = logout;
		$scope.semesterlist = []
		$scope.currSem = 4;
		$scope.courseForSemester = []
		$scope.availableCoursesForSemester = []
		$scope.allCoursesForSemester = []
		$scope.availableCourseList = []
		$scope.studentCourseList = []
		$scope.studentCourseList = []
		$scope.assignmentList = []
		$scope.userId;
		$scope.courseId;
		$scope.course;
		$scope.selected_course = {};
		
		
		function validate(){

			$scope.userId = $window.localStorage.getItem("sher_user_id");
			$scope.user_role = $window.localStorage.getItem("sher_user_role");


			if(!$scope.userId){
				window.location.replace("#/");
			} else {
				if($scope.user_role != "student"){
					window.location.replace("#/");
				} else {
					console.log("ROUTE")
					console.log($routeParams)
					init($routeParams.cid);
				}
			}	
		}
		
		validate();
		
		function init(rCid){
			

			// MenuService.setMenu([1,2,3]);

			var m = MenuService.getMenu();
				// var menuItems = res.data;
				if (m == undefined){
					console.log("NO MENU STORED")
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
		                    					console.log($routeParams.cid)
												if($routeParams.cid == null || $routeParams.cid == "" || $routeParams.cid == undefined){
													//redirect
													if($scope.courseForSemester.length!=0){

														window.location.replace("#/student/course/"+$scope.courseForSemester[0].course.courseId);
													}
												} else {
													//Get assignment
													console.log("GET ASSIGNMENT NOW")
													
													loadPage();
												}
		                });
					})
				} else {

					var newcid = rCid;
					console.log("already have that data")
					$scope.courseForSemester = m;
					console.log(newcid)
					if(!newcid){
						console.log("NEWCID")
						//redirect
						console.log("REDIRECTING")
						if($scope.courseForSemester.length!=0){
							
							window.location.replace("#/student/course/"+$scope.courseForSemester[0].course.courseId);

							}
				} else {
					//Get assignment
					loadPage();
				}

			}

		}

		function loadPage(){
			$scope.courseId = $routeParams.cid;
			if($routeParams.cid){
				console.log($scope.courseId)
				console.log("LOAD PAGE")
				console.log($scope.courseForSemester)
				var sel_c = $scope.courseForSemester.filter(function(x){
					return x.course.courseId == $scope.courseId;			
				});
				
				//
				console.log(sel_c)
				$scope.selected_course = sel_c[0].course;
				console.log($scope.selected_course)
			}
			$scope.courseId = $routeParams.cid;
			InstructorService
			.getCourseById($scope.courseId)
			.then(function(resp){
				$scope.course = resp.data;
			});
			getAllAssignmentsForCourseID($scope.courseId);

		}


		function getAllAssignmentsForCourseID(courseId){
			InstructorService
			.getAllAssignments(courseId)
			.then(function (response){
				$scope.assignmentList = response.data;
				console.log(response.data);
			}
			,function (error){

			})
		}

		function logout(){
			var auth2 = null;
			var googleUser = null;
			
			gapi.load('auth2', function() {//load in the auth2 api's, without it gapi.auth2 will be undefined
				api.auth2.init(
						{
							client_id: '360591443014-895c59hru7tn8ppdo0fdjh0kplggvn4p.apps.googleusercontent.com'
						}
				);
				
				auth2 = gapi.auth2.getAuthInstance();
				googleUser = auth2.currentUser.get();
				
				if(googleUser.isSignedIn()){
					auth2.signOut().then(function(){
						auth2.disconnect();
						auth2.currentUser.get().reloadAuthResponse();
						console.log("signed out");
						//$window.localStorage.clear();
						//$window.location.href="https://appengine.google.com/_ah/logout?continue=https://neuidmsso.neu.edu/logout.html?continue=http://localhost:8000";
					});
				}
				else{
					UserService.logout();
				}
				
			});
			var currentUrl = $window.location.href;

			$window.location.href="https://appengine.google.com/_ah/logout?continue=https://neuidmsso.neu.edu/logout.html?continue="+currentUrl;

			UserService.logout();
			//$location.url("https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue=https://neuidmsso.neu.edu/logout.html?continue=http://localhost:8000");
		}

	}

})();
