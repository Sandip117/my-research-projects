(function(){
	angular
	.module("UserDemo")
	.controller("assignmentController", assignmentController);

	function assignmentController(UserService,InstructorService,SemesterService,MenuService,SubmissionService,$location, $routeParams, $scope) {
    var vm = this;
    $scope.assignId = $routeParams.aid;
    $scope.courseId = $routeParams.cid;
    $scope.userId = $routeParams.uid;
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
    function init(){
      getAssignment();
      getUserById();
      getSubmissionsByAssignment();
			getMenu();
    }

    init();

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
  }

  })();
