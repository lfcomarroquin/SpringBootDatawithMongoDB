package org.adaschool.api.service.user;

import org.adaschool.api.repository.user.User;
import org.adaschool.api.repository.user.UserMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceMongoDb implements UsersService {

    private final UserMongoRepository userMongoRepository;

    @Autowired
    public UsersServiceMongoDb(UserMongoRepository userMongoRepository) {
        this.userMongoRepository = userMongoRepository;
    }

    @Override
    public User save(User user) {
        if (user.getName() == null || user.getLastName() == null|| user.getEmail() == null) {
            System.out.println("Por favor, ingrese nombre, apellido e email valido");
        }
        User entitySaved = this.userMongoRepository.save(user);
        System.out.println("El usuario ha sido creado con exito");
        return entitySaved;
    }

    @Override
    public Optional<User> findById(String id) {
        Optional<User> userOptional = this.userMongoRepository.findById(id);
        if (userOptional.isEmpty()){
            System.out.println("No se encontro ningun usuario con el ID: " + id);
        }
        return userOptional;
    }

    @Override
    public List<User> all() {
        return this.userMongoRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        Optional<User> userOptional = this.userMongoRepository.findById(id);
        if (userOptional.isEmpty()){
            System.out.println("No se encontro ningun usuario con el ID: " + id);
        }
        userMongoRepository.deleteById(id);
        System.out.println("Usuario con ID: " + id + " eliminado correctamente");
    }

    @Override
    public User update(User user, String userId) {
        Optional<User> userOptional = this.userMongoRepository.findById(userId);
        if (userOptional.isEmpty()){
            System.out.println("No se encontro ningun usuario con el ID: " + userId);
            return null;
        }
        User updatedUser = userOptional.get();
        updatedUser.setName(user.getName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setEmail(user.getEmail());
        User updatedUserEntity = this.userMongoRepository.save(updatedUser);
        System.out.println("Usuario con ID: " + userId + " modificado correctamente");
        return updatedUserEntity;
    }
}