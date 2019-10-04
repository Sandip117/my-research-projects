(function(){
	angular
	.module("UserDemo")
	.controller("courseRegController", courseRegController);

	function courseRegController(SemesterService, UserService,InstructorService, $location, $routeParams, $scope) {
		var vm = this;
    vm.selectSemesterId = selectSemesterId;
    $scope.semesterlist = []
		$scope.courseForSemester = [];
		$scope.availableCoursesForSemester = []
		$scope.allCoursesForSemester = []
		$scope.availableCourseList = []
    $scope.studentCourseList = [];
		$scope.studentRegisteredCourses = []
		vm.selectAvailableCoursesForSemester = selectAvailableCoursesForSemester
		vm.changeCourse = changeCourse
		vm.deleteCourse = deleteCourse
		vm.course;
		vm.addCourse = addCourse

		function init(){
			SemesterService
			.getAllCourses($routeParams.uid)
			.then(function(response){
				$scope.studentRegisteredCourses = response.data
			})
			InstructorService
			.getAllSemesters()
			.then(function(response){
				$scope.semesterlist = response.data;

			})

		}
		init();

		function addCourse(){
			UserService
			.findUserById($routeParams.uid)
			.then(function(response){
				var userData = response.data
				var courseJson = {student: userData, course: vm.course}
				SemesterService
				.addStudentCourse(courseJson)
				.then(function(response){
					$location.url("/student/"+$routeParams.uid)
					console.log(response)
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
						if(courseAvailable)
							$scope.availableCourseList.push($scope.allCoursesForSemester[j])
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
					for(x in $scope.studentRegisteredCourses){
						if(rid==$scope.studentRegisteredCourses[x].registrationId){
							$scope.studentRegisteredCourses.splice(x,1);
						}
						break;
					}
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
                            $scope.courseForSemester.push($scope.studentRegisteredCourses[i].course)
                    }
                    console.log("COURSE FOR SEM")
                    console.log($scope.courseForSemester)

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
