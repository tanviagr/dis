package sgsits.cse.dis.administration.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sgsits.cse.dis.administration.model.ECCWComplaint;
import sgsits.cse.dis.administration.repo.ECCWComplaintRepository;
import sgsits.cse.dis.administration.request.ECCWComplaintForm;
import sgsits.cse.dis.administration.request.EditComplaintForm;
import sgsits.cse.dis.administration.service.ECCWComplaintService;

@Service
public class EccwComplaintServiceImpl implements ECCWComplaintService {
	@Autowired
	ECCWComplaintRepository eccwComplaintRepository;

	@Override
	public List<ECCWComplaint> findAllRemainingComplaints(List<String> location) {
		return eccwComplaintRepository.findByLocationInAndStatusNot(location, "Resolved");
	}

	@Override
	public List<ECCWComplaint> addMultipleComplaint(List<ECCWComplaintForm> eccwComplaints, String userId) {
		/*
		 * long formId = eccwComplaintRepository.maxOfFormId(); int size =
		 * eccwComplaintForm.size(); int count = 0;
		 */
		List<ECCWComplaint> eccwComplaintList = new ArrayList<ECCWComplaint>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for (ECCWComplaintForm complaintForm : eccwComplaints) {
			if (!eccwComplaintRepository.existsByLocationAndDetailsAndStatusNot(complaintForm.getLocation(),
					complaintForm.getDetails(), "Resolved")) {
				ECCWComplaint eccwComplaint = new ECCWComplaint();
				eccwComplaint.setStatus("Not Resolved");
				eccwComplaint.setCreatedBy(userId);
				eccwComplaint.setCreatedDate(simpleDateFormat.format(new Date()));
				eccwComplaint.setLocation(complaintForm.getLocation());
				eccwComplaint.setDetails(complaintForm.getDetails());
				eccwComplaint.setType("ECCW");
				eccwComplaintList.add(eccwComplaint);
			}
		}
		List<ECCWComplaint> test = eccwComplaintRepository.saveAll(eccwComplaintList);
		return test;

	}

	@Override
	public ECCWComplaint editComplaint(EditComplaintForm editComplaintForm, String userId) {
		ECCWComplaint test = null;
		Optional<ECCWComplaint> eccw = eccwComplaintRepository.findById(editComplaintForm.getId());
		if (eccw.isPresent()) {
			eccw.get().setModifiedBy(userId);
			eccw.get().setModifiedDate((new SimpleDateFormat()).format(new Date()));
			eccw.get().setStatus(editComplaintForm.getStatus());
			eccw.get().setRemarks(editComplaintForm.getRemarks());
			if (editComplaintForm.getStatus().equals("Resolved"))
				eccw.get().setDateOfResolution((new SimpleDateFormat()).format(new Date()));
			test = eccwComplaintRepository.save(eccw.get());
		}
		return test;
	}

	@Override
	public long countByLocationInAndStatusNot(List<String> locations, String status) {
		return eccwComplaintRepository.countByLocationInAndStatusNot(locations, "Resolved");
	}

	@Override
	public long countByLocationInAndStatus(List<String> locations, String status) {
		return eccwComplaintRepository.countByLocationInAndStatus(locations, "Resolved");
	}

	@Override
	public long countByLocationIn(List<String> loctions) {
		return eccwComplaintRepository.countByLocationIn(loctions);
	}

}
