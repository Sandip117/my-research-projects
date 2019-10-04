(function(){
    angular
        .module("UserDemo")
        .factory("InstructorService",InstructorService);
    
   
    function InstructorService($http) {
    	var api = {
            "getAllAssignments":getAllAssignments,
            "deleteAssignment": deleteAssignment,
            "deleteCourse": deleteCourse,
            "addCourse": addCourse,
            "addAssignment": addAssignment,
            "getAllCourses":getAllCourses,
            "getAllSemesters": getAllSemesters,
            "getSemesterById": getSemesterById,
            "getCourseById": getCourseById,
            "getAssignmentById": getAssignmentById,
            "updateAssignment": updateAssignment,
            "getCourseBySemester": getCourseBySemester,
            "putAllCourses": putAllCourses,
            "getStoredCourses": getStoredCourses,
            "putAllAssignmentsNo": putAllAssignmentsNo,
            "getStoredAssign": getStoredAssign,
            "putAllReportsNo": putAllReportsNo,
            "getAllReportsNo": getAllReportsNo
        };
        return api;   
        function getAllAssignments(courseId){
        	return $http.get("/api/assignment?courseId="+courseId);
        }
        function getAllCourses(userId){
        	return $http.get("/api/courses?userId="+userId);
        }
        function addAssignment(assignment){
        	console.log(assignment)
        	return $http.post("/api/assignment",assignment);
        }
        function addCourse(course){
        	return $http.post("/api/course",course);
        }
        function deleteCourse(courseId,userId){
        	return $http.delete("/api/course?courseId="+courseId+"&userId="+userId);
        }
        function deleteAssignment(assign){
        	return $http.delete("/api/assignment", assign);
        }
        function getAllSemesters(){
        	return $http.get("/api/semesters");
        }
        
        function getSemesterById(sid){
        	console.log(sid);
        	return $http.get("/api/semester?semId="+sid);
        }
        
        function getCourseById(cid){
        	return $http.get("/api/course?courseId="+cid);
        }
        function getAssignmentById(aid){
        	return $http.get("/api/assignments?assignmentId="+aid);
        }
        function updateAssignment(assignment){
        		return $http.put("/api/assignment", assignment)
        }
        
        function getCourseBySemester(semester){
        	return $http.post("/api/semester/course", semester);
        }
        
        var all_courses;
        function putAllCourses(courses){
        	all_courses = courses;
        }
        
        function getStoredCourses(){
        	return all_courses;
        }
        var all_assignments;
        function putAllAssignmentsNo(assign_num){
        	all_assignments = assign_num;
        }

        function getStoredAssign(){
        	return all_assignments;
        }
        
        var all_reports;
        function putAllReportsNo(report_num){
        	all_reports = report_num;
        }
        
        function getAllReportsNo(){
        	return all_reports;
        }
        
        
        
    }
})();