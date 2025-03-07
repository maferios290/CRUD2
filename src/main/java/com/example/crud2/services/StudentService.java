package com.example.crud2.services;

import com.example.crud2.entities.StudentEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {

    // Lista de estudiantes en memoria
    private final List<StudentEntity> students = new ArrayList<>();

    public StudentService() {
        // Agregar estudiantes con IDs fijos
        students.add(new StudentEntity(1L, "Juan", "Pérez", "juan.perez@mail.com"));
        students.add(new StudentEntity(2L, "Ana", "Gómez", "ana.gomez@mail.com"));
        students.add(new StudentEntity(3L, "Luis", "Rodríguez", "luis.rodriguez@mail.com"));
    }

    // Método para obtener todos los estudiantes
    public List<StudentEntity> getAllStudents() {
        return students;
    }

    // Método para obtener un estudiante por su ID
    public ResponseEntity<?> getStudentById(Long id) {
        Optional<StudentEntity> student = students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();

        return student.isPresent() ? ResponseEntity.ok(student.get()) :
                ResponseEntity.badRequest().body(Map.of("Error", "Estudiante con ID " + id + " no encontrado"));
    }

    // Método para agregar un nuevo estudiante
    public ResponseEntity<?> addStudent(StudentEntity student) {
        boolean exists = students.stream()
                .anyMatch(s -> s.getId().equals(student.getId()));

        if (exists) {
            return ResponseEntity.badRequest().body(Map.of("Error", "El estudiante con ID " + student.getId() + " ya existe"));
        }
        students.add(student);
        return ResponseEntity.ok(Map.of("Mensaje", "Estudiante agregado exitosamente", "estudiante", student));
    }

    // Método para actualizar un estudiante por su ID
    public ResponseEntity<?> updateStudent(Long id, StudentEntity studentDetails) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                studentDetails.setId(id);
                students.set(i, studentDetails);
                return ResponseEntity.ok(Map.of("Mensaje", "Estudiante actualizado exitosamente", "estudiante", studentDetails));
            }
        }
        return ResponseEntity.badRequest().body(Map.of("Error", "Estudiante con ID " + id + " no encontrado"));
    }

    // Método para eliminar un estudiante por ID fijo (ID 1)
    public ResponseEntity<?> deleteStudentById(Long id) {
        // Usamos un ID fijo para la eliminación (por ejemplo, ID 1)
        Long fixedId = 1L; // ID fijo para el estudiante que queremos eliminar
        Optional<StudentEntity> studentToRemove = students.stream()
                .filter(s -> s.getId().equals(fixedId))
                .findFirst();

        if (studentToRemove.isPresent()) {
            students.remove(studentToRemove.get());
            return ResponseEntity.ok(Map.of("Mensaje", "Estudiante eliminado exitosamente"));
        } else {
            return ResponseEntity.badRequest().body(Map.of("Error", "Estudiante con ID " + fixedId + " no encontrado"));
        }
    }
}
