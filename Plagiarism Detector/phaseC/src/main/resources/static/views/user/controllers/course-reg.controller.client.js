(function(){
	angular
	.module("UserDemo")
	.controller("courseRegController", courseRegController);

	function courseRegController(SemesterService, UserService,InstructorService,MenuService, $location, $routeParams, $scope, $window) {
		var vm = this;
		vm.userId;
		vm.logout = logout;
    vm.selectSemesterId = selectSemesterId;
    $scope.semesterlist = []
		$scope.currSem = 4;
		$scope.courseForSemester = [];
		$scope.availableCoursesForSemester = []
		$scope.allCoursesForSemester = []
		$scope.availableCourseList = []
    $scope.studentCourseList = [];
		$scope.studentRegisteredCourses = []
		$scope.menuItems = [];
		$scope.userId;
		vm.selectAvailableCoursesForSemester = selectAvailableCoursesForSemester
		vm.changeCourse = changeCourse
		vm.deleteCourse = deleteCourse
		vm.course;
		vm.addCourse = addCourse

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
			SemesterService
			.getAllCourses($scope.userId)
			.then(function(response){
				$scope.studentRegisteredCourses = response.data
				getMenu();
			})
			InstructorService
			.getAllSemesters()
			.then(function(response){
				$scope.semesterlist = response.data;

			})

		}

		function getMenu(){
			var m = MenuService.getMenu();
			if (m == undefined){
				updateMenu();
			} else {
				$scope.menuItems = m;
			}
		}

		function updateMenu(){
			InstructorService
							.getSemesterById($scope.currSem)
							.then(function(resp){
								console.log("INSIDE")
								console.log(resp)
								$scope.menuItems = [];
									var selectedSemesterId = resp.data.semesterId;
									for (var i=0;i<$scope.studentRegisteredCourses.length;i++){
											var semId = $scope.studentRegisteredCourses[i].course.semester.semesterId;
											if(semId == selectedSemesterId){
													$scope.menuItems.push($scope.studentRegisteredCourses[i])
												}
											}
											console.log("MENU")
											console.log($scope.studentRegisteredCourses)
											console.log($scope.menuItems)
											MenuService.setMenu($scope.menuItems);

										});
		}

		function updateAllCourses(){
			console.log($scope.semester)
			$scope.studentRegisteredCourses = []
			SemesterService
				.getAllCourses($scope.userId)
				.then(function(response){
					console.log("SUCCESS")
					console.log(response.data)
					$scope.studentRegisteredCourses = response.data
					console.log("SRC ONE MORE")
					console.log($scope.userId)
					console.log($scope.studentRegisteredCourses)
					selectSemesterId($scope.semester);
					updateMenu();
				})

			// selectAvailableCoursesForSemester($scope.semester.semesterId);
		}

		function addCourse(c){
			UserService
			.findUserById($scope.userId)
			.then(function(response){
				var userData = response.data
				var courseJson = {student: userData, course: c}
				SemesterService
				.addStudentCourse(courseJson)
				.then(function(response){
					console.log("ADD COURSE")
					console.log(response)
					updateAllCourses();
				})
			})
		}
		function changeCourse(course){
			vm.course = course
			vm.semesterName = course.semester.name
			vm.facultyEmail = course.faculty.emailId
		}

		function selectAvailableCoursesForSemester(semId){
			$scope.availableCourseList = []
			InstructorService
			.getSemesterById(semId)
			.then(function(resp){

				InstructorService
				.getCourseBySemester(resp.data)
				.then(function(response){
					$scope.allCoursesForSemester = response.data
					console.log($scope.studentRegisteredCourses)

					for (var j=0;j<$scope.allCoursesForSemester.length;j++){
						var otherRequiredIds = $scope.allCoursesForSemester[j].courseId
						var courseAvailable = true
						for (var i=0;i<$scope.studentRegisteredCourses.length;i++){
							var courseId = $scope.studentRegisteredCourses[i].course.courseId
							if (otherRequiredIds == courseId){
								courseAvailable = false
							}
						}
						if(courseAvailable){
							$scope.availableCourseList.push($scope.allCoursesForSemester[j])
						}
					}

					console.log($scope.availableCourseList)
				})
			})
		}

		function deleteCourse(rid){
			console.log(rid);
			var r = confirm("Are you sure?");
			if(r == true){
				SemesterService
				.deleteCourse(rid)
				.then(function (response){
					// for(x in $scope.studentRegisteredCourses){
					// 	if(rid==$scope.studentRegisteredCourses[x].registrationId){
					// 		$scope.studentRegisteredCourses.splice(x,1);
					// 	}
					// 	break;
					// }
					updateAllCourses();
				})
				,function (error){
					vm.error="unable to delete";
				}
			}
		}


        function selectSemesterId(sem){
            $scope.courseForSemester = [];
            console.log($scope.courseForSemester)


            vm.semesterId = $scope.semester.semesterId;
            InstructorService
                .getSemesterById($scope.semester.semesterId)
                .then(function(resp){
                    var selectedSemesterId = resp.data.semesterId;
                    for (var i=0;i<$scope.studentRegisteredCourses.length;i++){
                        var semId = $scope.studentRegisteredCourses[i].course.semester.semesterId;
                        if(semId == selectedSemesterId)
                            $scope.courseForSemester.push($scope.studentRegisteredCourses[i])
                    }
                    console.log("COURSE FOR SEM")
                    console.log($scope.courseForSemester)
										console.log("SRC")
										console.log($scope.studentRegisteredCourses)
                    InstructorService
                        .getCourseBySemester(resp.data)
                        .then(function(response){
                            $scope.availableCoursesForSemester = response.data;
                            console.log("AVAILABLE COURSES")
                            console.log($scope.availableCoursesForSemester)

                        })

                });
            selectAvailableCoursesForSemester(vm.semesterId);

            console.log("END SELECT")
        }
        
        function logout(){
        	var auth2 = null;
			var googleUser = null;

			gapi.load('auth2', function() {//load in the auth2 api's, without it gapi.auth2 will be undefined
				gapi.auth2.init(
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
					});
				}
			});
			var currentUrl = $window.location.href;

			$window.location.href="https://appengine.google.com/_ah/logout?continue=https://neuidmsso.neu.edu/logout.html?continue="+currentUrl;
			UserService.logout();
		}
	}
})();
