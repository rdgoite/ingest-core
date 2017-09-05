package org.humancellatlas.ingest.project.web;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.humancellatlas.ingest.envelope.SubmissionEnvelope;
import org.humancellatlas.ingest.project.Project;
import org.humancellatlas.ingest.project.ProjectService;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Javadocs go here!
 *
 * @author Tony Burdett
 * @date 05/09/17
 */
@RepositoryRestController
@ExposesResourceFor(Project.class)
@RequiredArgsConstructor
@Getter
public class ProjectController {
    private final @NonNull ProjectService projectService;

    @RequestMapping(path = "submissionEnvelopes/{sub_id}/projects", method = RequestMethod.POST)
    HttpEntity<Project> addProjectToEnvelope(@PathVariable("sub_id") SubmissionEnvelope submissionEnvelope,
                                             @RequestBody Project project) {
        Project entity = getProjectService().addProjectToSubmissionEnvelope(submissionEnvelope, project);
        return ResponseEntity.accepted().body(entity);
    }
}