package dev.project.mastersys.infra.specifications;

import dev.project.mastersys.application.dtos.StudentFilterRequestDTO;
import dev.project.mastersys.domain.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

    public static Specification<Student> withFilters(StudentFilterRequestDTO filter) {
        return Specification
                .where(nameContains(filter.name()))
                .and(emailContains(filter.email()))
                .and(phoneContains(filter.phone()))
                .and(cityContains(filter.city()))
                .and(stateEquals(filter.state()));
    }

    private static Specification<Student> nameContains(String name) {
        return (root, query, cb) -> name == null || name.isBlank() 
                ? cb.conjunction() 
                : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    private static Specification<Student> emailContains(String email) {
        return (root, query, cb) -> email == null || email.isBlank() 
                ? cb.conjunction() 
                : cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    private static Specification<Student> phoneContains(String phone) {
        return (root, query, cb) -> phone == null || phone.isBlank() 
                ? cb.conjunction() 
                : cb.like(root.get("phone"), "%" + phone + "%");
    }

    private static Specification<Student> cityContains(String city) {
        return (root, query, cb) -> city == null || city.isBlank() 
                ? cb.conjunction() 
                : cb.like(cb.lower(root.get("city")), "%" + city.toLowerCase() + "%");
    }

    private static Specification<Student> stateEquals(String state) {
        return (root, query, cb) -> state == null || state.isBlank() 
                ? cb.conjunction() 
                : cb.equal(root.get("state"), state);
    }
}
