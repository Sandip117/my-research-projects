(function(){
	angular
	.module("UserDemo")
	.controller("studentController", studentController);

	function studentController(UserService,InstructorService,SemesterService,MenuService,$location, $routeParams, $scope) {
		var vm = this;
		vm.userId = $routeParams.uid;
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
		function init(){
			$scope.userId = $routeParams.uid;

			// MenuService.setMenu([1,2,3]);

			var m = MenuService.getMenu();
			console.log("M@")
			console.log(m);
				// var menuItems = res.data;
				if (m == undefined){
					console.log("NO MENU STORED")
					SemesterService
					.getAllCourses($routeParams.uid)
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

												if($routeParams.cid == null || $routeParams.cid == "" || $routeParams.cid == undefined){
													//redirect
													if($scope.courseForSemester.length!=0){

														window.location.replace("#/student/"+$routeParams.uid+"/course/"+$scope.courseForSemester[0].course.courseId);
													}
												} else {
													//Get assignment
													console.log("GET ASSIGNMENT NOW")

													loadPage();
												}
		                });
					})
				} else {

					console.log("already have that data")
					$scope.courseForSemester = m;

					if($routeParams.cid == null || $routeParams.cid == "" || $routeParams.cid == undefined){
						//redirect
						if($scope.courseForSemester.length!=0){

							window.location.replace("#/student/"+$routeParams.uid+"/course/"+$scope.courseForSemester[0].course.courseId);
						}
					} else {
						//Get assignment
						console.log("GET ASSIGNMENT NOW")

						loadPage();
					}

				}

		}
		init();

		function loadPage(){
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

	}

})();
