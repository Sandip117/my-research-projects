(function(){
	angular
	.module("UserDemo")
	.controller("courseRegController", courseRegController);

	function courseRegController(SemesterService, UserService,InstructorService,MenuService, $location, $routeParams, $scope) {
		var vm = this;
		vm.userId;
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

		function init(){
			$scope.userId = $routeParams.uid;
			SemesterService
			.getAllCourses($routeParams.uid)
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
		init();

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
				.getAllCourses($routeParams.uid)
				.then(function(response){
					console.log("SUCCESS")
					console.log(response.data)
					$scope.studentRegisteredCourses = response.data
					console.log("SRC ONE MORE")
					console.log($routeParams.uid)
					console.log($scope.studentRegisteredCourses)
					selectSemesterId($scope.semester);
					updateMenu();
				})

			// selectAvailableCoursesForSemester($scope.semester.semesterId);
		}

		function addCourse(c){
			UserService
			.findUserById($routeParams.uid)
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
	}
})();
