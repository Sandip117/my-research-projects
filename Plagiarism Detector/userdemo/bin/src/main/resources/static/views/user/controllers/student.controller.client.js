(function(){
	angular
	.module("UserDemo")
	.controller("studentController", studentController);

	function studentController(UserService,InstructorService,SemesterService,$location, $routeParams, $scope) {
		var vm = this;
		vm.userId = $routeParams.uid;
		$scope.semesterlist = []
		$scope.courseForSemester = []
		$scope.availableCoursesForSemester = []
		$scope.allCoursesForSemester = []
		$scope.availableCourseList = []
		$scope.studentCourseList = []
		$scope.studentCourseList = []
		function init(){
			SemesterService
			.getAllCourses($routeParams.uid)
			.then(function(response){
				$scope.studentCourseList = response.data
				console.log($scope.studentCourseList)
			})
			
		}
		init();

		
		
	}

})();
