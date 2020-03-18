package sgsits.cse.dis.administration.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgsits.cse.dis.administration.model.OtherComplaint;
import sgsits.cse.dis.administration.repo.OtherComplaintRepository;
import sgsits.cse.dis.administration.request.EditComplaintForm;
import sgsits.cse.dis.administration.request.OtherComplaintForm;
import sgsits.cse.dis.administration.service.OtherComplaintService;

@Service
public class OtherComplaintServiceImpl implements OtherComplaintService {

	@Autowired
	OtherComplaintRepository otherComplaintRepository;

	@Override
	public List<OtherComplaint> findAllRemainingComplaints(List<String> location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OtherComplaint addComplaint(OtherComplaintForm complaintForm, String userId) {
		OtherComplaint otherComplaint = new OtherComplaint();
		otherComplaint.setDetails(complaintForm.getDetails());
		otherComplaint.setCreatedBy(userId);
		otherComplaint.setCreatedDate((new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(new Date()));
		otherComplaint.setStatus("Not Assigned");
		otherComplaint.setType("OTHER");
		OtherComplaint test = otherComplaintRepository.save(otherComplaint);
		return test;
	}

	@Override
	public boolean checkIfComplaintExist(String id, String details, String status) {
		if (!otherComplaintRepository.existsByCreatedByAndDetailsAndStatusNot(id, details, status)) {
			return true;
		}
		return false;
	}

	@Override
	public OtherComplaint editComplaint(EditComplaintForm editComplaintForm, String userId) {
		Optional<OtherComplaint> other = otherComplaintRepository.findById(editComplaintForm.getId());
		OtherComplaint test = null;
		if (other.isPresent()) {
			other.get().setModifiedBy(userId);
			other.get().setModifiedDate((new SimpleDateFormat()).format(new Date()));
			other.get().setStatus(editComplaintForm.getStatus());
			other.get().setRemarks(editComplaintForm.getRemarks());
			other.get().setAssignedTo(editComplaintForm.getAssignedTo());
			if (editComplaintForm.getStatus().equals("Resolved"))
				other.get().setDateOfResolution((new SimpleDateFormat()).format(new Date()));
			test = otherComplaintRepository.save(other.get());
		}
		return test;
	}

	@Override
	public long countByStatusNot(String status) {
		return  otherComplaintRepository.countByStatusNot("Resolved");
	}

	@Override
	public long countByAssignedToAndStatusNot(String id, String status) {
		return otherComplaintRepository.countByAssignedToAndStatusNot(id, status);
	}

	@Override
	public long countByAssignedToAndStatus(String id, String status) {
		return otherComplaintRepository.countByAssignedToAndStatus(id, status);
	}

	@Override
	public long countByStatus(String status) {
		return otherComplaintRepository.countByStatus(status);
	}

	@Override
	public long countByAssignedTo(String userId) {
		return otherComplaintRepository.countByAssignedTo(userId);
	}

	@Override
	public long countByCreatedBy(String userId) {
		return otherComplaintRepository.countByCreatedBy(userId);
	}

	@Override
	public long countAll() {
		return otherComplaintRepository.count();
	}

	@Override
	public List<OtherComplaint> findAll() {
		return otherComplaintRepository.findAll();
	}

	@Override
	public List<OtherComplaint> findByAssignedTo(String id) {
		return otherComplaintRepository.findByAssignedTo(id);
	}

}
