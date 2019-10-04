(function(){
	angular
	.module("UserDemo")
	.controller("instructorController", instructorController);

	function instructorController(UserService,InstructorService,SubmissionService,$location, $routeParams, $scope, $sce) {
		$scope.name="sample";
		$scope.usId = $routeParams.uid;
		console.log($routeParams)
		var ecourse;
		var cid;
		var assignmentResponse;
		var vm = this;
		vm.assignmentsForCourse=[];
		vm.coursesForUser=[];
		vm.userId = $routeParams.uid;
		$scope.userId = vm.userId;
		vm.getAllAssignmentsForCourseID = getAllAssignmentsForCourseID;
		vm.courseId = $routeParams.cid;
		vm.createCourse = createCourse;
		vm.addAssignment = addAssignment;
		vm.deleteAssignment = deleteAssignment;
		vm.addCourse = addCourse;
		vm.getAllSemesters = getAllSemesters;
		vm.deleteCourse = deleteCourse;
		vm.editCourse = editCourse;
		vm.patchCourse = patchCourse;
		vm.patchAssignment = patchAssignment;
		vm.editAssignment = editAssignment;
		//vm.getStudents = getStudents;
		//vm.getReport = getReport;
		//vm.getStats = getStats;
		vm.getAllAssignmentsForCourseID = getAllAssignmentsForCourseID;
		vm.getAllCoursesForUserID = getAllCoursesForUserID;
		vm.getAssignmentById = getAssignmentById;
		vm.viewReport = viewReport;
		vm.loadReports = loadReports;
		vm.setThreshold = setThreshold;

		getAllCoursesForUserID(vm.userId);
		getAllSemesters();
		$scope.courselist= [];
		$scope.assignmentlist= [];
		$scope.semlist = [];
		$scope.uid=this.userId;
		$scope.c_title = "";
		$scope.c_id="";
		$scope.c_sem="";
		$scope.assignmentName="";
		$scope.assignmentId="";
		$scope.enabledEdit = []
		$scope.allReportList = [];
		$scope.userList = [];
		$scope.assignName = "";
		$scope.selectedCourseForAssignment = {};
		$scope.reportStudent1 = "";
		$scope.reportStudent2 = "";
		$scope.reportPc = "";
		$scope.reportLinesCopied = "";
		$scope.reportFirstSide = "";
		$scope.reportSecondSide = "";
		$scope.threshList = [];
		$scope.amid = {};

		if (this.courseId!=""){
			getCourseById(this.courseId);
		}

//		function addAssignment(){

//		$location.url("/instructor/addCourse");
//		InstructorService
//		.addAssignment(a)
//		.then(function (response){
//		$location.url("/instructor/addCourse");
//		}
//		,function (error){

//		})
//		}

	function init(){
		if($scope.assignmentList == undefined){
			getAllAssignmentsForCourseID($scope.courselist[0]);
		}
	}

	function loadReports(assignment){
		getAllReports(assignment.assignmentId);
	}

	function setThreshold(tVal){
		if(tVal!=null){

			if(tVal>100 || tVal<0){
				alert("Threshold value cannot be greater than 100 or less than 0!");
			} else {
				if($scope.allReportList.length>0){
					var allReps = $scope.allReportList;
					var newReps = [];

					for(var i=0; i<allReps.length;i++){
						if(allReps[i].plagiarismScore >= tVal){
							newReps.push(allReps[i]);
						}
					}

					console.log(newReps)
					$scope.threshList = newReps;

				}
			}

		}
	}

	function getAllReports(assignId){

		InstructorService
		.getAssignmentById(assignId)
		.then(function (resp){
			$scope.assignName = resp.data.assignmentName;
		});


		console.log("GET ALL REPORTS")
		SubmissionService
		.getReportByAssignId(assignId)
		.then(function(resp){
			for (var i=0; i<resp.data.length; i++){
				console.log("resp data")
				console.log(resp.data[i])
				var u1_id = resp.data[i].user1Id;
				var u2_id = resp.data[i].user2Id;
				var mid = resp.data[i].mossId;
				console.log($scope.amid)
				getStudentNames(u1_id,u2_id,mid);

			}

			console.log("USER LIST")
			console.log($scope.userList)
			$scope.allReportList = resp.data;
			$scope.threshList = resp.data;


			console.log("REPORT LIST")
			console.log($scope.allReportList)
		});
	}

	function getStudentNames(u1_id,u2_id, mid){
		$scope.userList[mid] = {}

		UserService
		.findUserById(u1_id)
		.then(function (response){
			console.log("mid")
			console.log($scope.amid)
			$scope.userList[mid].user1Id = response.data;
		});
		UserService
		.findUserById(u2_id)
		.then(function (response){
			$scope.userList[mid].user2Id = response.data;
		});
	}

	$scope.trustSrc = function(src) {
    	return $sce.trustAsResourceUrl(src);
  	}

		function viewReport(report){
			console.log("report")
			console.log(report)
			$scope.reportStudent1 = $scope.userList[report.mossId].user1Id.emailId;
			$scope.reportStudent2 = $scope.userList[report.mossId].user2Id.emailId;
			$scope.reportPc = report.plagiarismScore;
			$scope.reportLinesCopied = report.linesCopied;
			var link = report.mossLink;
			var newLink = link.substring(0, link.length-5);
			var firstSide = newLink+"-0.html";
			var secondSide = newLink+"-1.html";
			console.log(firstSide)
			console.log(secondSide)
			$scope.reportRegScore = report.regressionScore;
			$scope.reportFirstSide = firstSide;
			$scope.reportSecondSide = secondSide;
		}

		function createCourse(){
			console.log("create")
			$location.url("/instructor/"+this.userId+"/addCourse")

		}

		function addAssignment(assignment){
			InstructorService
			.getCourseById(vm.courseId)
			.then(function (response){
				assignment.course = response.data
				InstructorService
				.addAssignment(assignment)

				.then(function (response){
					$location.url("/instructor/"+vm.userId);
				}
				,function (error){
					console.log("sfd");

				})
			}
			,function (error){
				console.log("sfd");

			})


		}

		function addCourse(c){
			var semId = parseInt(c.semester);
			InstructorService
			.getSemesterById(semId)
			.success(function (sem){
				c.semester = sem;
				console.log(c)

				UserService
				.findUserById(vm.userId)
				.success(function (user) {
					c.faculty=user;
					console.log(c)

					InstructorService
					.addCourse(c)
					.then(function (response){
						console.log(c);
						$scope.courselist= getAllCoursesForUserID(vm.userId);
					}
					,function (error){
						alert(error);
					})
				});

			});



		}

		function getAllAssignmentsForCourseID(course){
			vm.thisCourse = course
			console.log(vm.thisCourse)
			InstructorService
			.getAllAssignments(vm.thisCourse.courseId)
			.then(function (response){
				$scope.assignmentlist = response.data;
				console.log(response.data);
				console.log("NEW")
				if(response.data[0] != undefined){
					getAllReports(response.data[0].assignmentId);
				} else {
					$scope.allReportList = [];
					$scope.threshList = [];
				}
			}
			,function (error){

			})
		}

		function getAllCoursesForUserID(userId){
			InstructorService
			.getAllCourses(userId)
			.then(function (response){
				var courses=response.data;

				console.log(response.data);
				$scope.courselist = response.data;
				console.log($scope.courselist);
				vm.coursesForUser=response.data;
				init();

			}
			,function (error){

			})
		}


		function deleteAssignment(aid){
			console.log(aid);
			var r = confirm("Are you sure?");
			if(r == true){
				InstructorService
				.deleteAssignment(aid,this.courseId)
				.then(function (response){
					for(x in vm.assignmentsForCourse){
						if(aid==vm.assignmentsForCourse[x].id){
							vm.assignmentsForCourse.splice(x,1);
						}
						getAllAssignmentsForCourseID(vm.courseId)
						break;
					}
				}
				,function (error){
					vm.error="unable to delete";
				})
			}
		}

		function deleteCourse(cid){
			console.log(cid);
			var r = confirm("Are you sure?");
			if(r == true){
				InstructorService
				.deleteCourse(cid,this.userId)
				.then(function (response){
					for(x in vm.coursesForUser){
						if(cid==vm.coursesForUser[x].id){
							vm.coursesForUser.splice(x,1);
						}
						getAllCoursesForUserID(vm.userId)
						break;
					}
				}
				,function (error){
					vm.error="unable to delete";
				})
			}
		}

		function getAllSemesters(){
			InstructorService
			.getAllSemesters()
			.then(function(response){
				console.log(response.data)
				$scope.semlist = response.data;
			}
			,function (error){

			})
		}
		function editAssignment(aid){

//			console.log(this.assignmentResponse)
			$location.url("/instructor/"+this.userId+"/editassignment/"+aid);

		}

		function getAssignmentById(aid){

			InstructorService
			.getAssignmentById(aid)
			.then(function(response){
				this.assignmentResponse = response.data;
				$scope.selectedCourseForAssignment = this.assignmentResponse.course;
				$scope.assignmentName = this.assignmentResponse.assignmentName
				$scope.assignmentId = this.assignmentResponse.assignmentId;

//				InstructorService
//				.updateAssignment(assignmentResponse)
//				.then(function(resp){
//
//				})
			}
			,function (error){

			})
		}

		function editCourse(cid){
			console.log(cid)
			console.log("Edit Course")
			getCourseById(cid);
			$location.url("/instructor/"+this.userId+"/editCourse/"+cid);
		}

		function getCourseById(cid){
			console.log(cid)
			InstructorService
			.getCourseById(cid)
			.then(function (response){
				this.ecourse=response.data;
				$scope.c_title = this.ecourse.courseName;
				$scope.c_id = this.ecourse.courseId;
				console.log(this.ecourse)
			}
			,function (error){

			})
		}

		function patchCourse(c){
			if(c!=null){
				if(c.semester!=null){
					var semId = parseInt(c.semester);

					InstructorService
					.getSemesterById(semId)
					.success(function (sem){
						this.ecourse.semester = sem;
						console.log(this.ecourse)

						if(c.courseName!=null){
							this.ecourse.courseName = c.courseName;
						}

						InstructorService
						.addCourse(this.ecourse)
						.then(function (response){
							console.log(this.userId)
							$location.url("/instructor/"+$routeParams.uid+"/addCourse")
						}
						,function (error){
							alert(error);
						})

					});

				}

			}
		}

		function patchAssignment(a){
			a.assignmentId = $routeParams.aid
			InstructorService
			.getAssignmentById($routeParams.aid)
			.then(function(response){
				this.assignmentResponse = response.data;
				$scope.selectedCourseForAssignment = this.assignmentResponse.course;
				$scope.assignmentName = this.assignmentResponse.assignmentName
				$scope.assignmentId = this.assignmentResponse.assignmentId;
				a.course = $scope.selectedCourseForAssignment;
				InstructorService
				.updateAssignment(a)
				.then(function(resp){
					$location.url("/instructor/"+$routeParams.uid)
					})
				})
		}

	}

})();
