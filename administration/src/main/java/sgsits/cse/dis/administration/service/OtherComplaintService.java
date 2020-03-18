package sgsits.cse.dis.administration.service;

import java.util.List;

import org.springframework.stereotype.Service;

import sgsits.cse.dis.administration.model.OtherComplaint;
import sgsits.cse.dis.administration.request.EditComplaintForm;
import sgsits.cse.dis.administration.request.OtherComplaintForm;

@Service
public interface OtherComplaintService{
	boolean checkIfComplaintExist(String id, String details, String status);
	List<OtherComplaint> findAllRemainingComplaints(List<String> location);
	OtherComplaint addComplaint(OtherComplaintForm complaintForm, String userId);
	OtherComplaint editComplaint(EditComplaintForm complaintForm, String userId);
	long countByStatusNot(String status);
	long countByStatus(String status);
	long countByAssignedToAndStatusNot(String id, String string);
	long countByAssignedToAndStatus(String id, String status);
	long countByAssignedTo(String userId);
	long countByCreatedBy(String userId);
	long countAll();
	List<OtherComplaint> findAll();
	List<OtherComplaint> findByAssignedTo(String id);
}
