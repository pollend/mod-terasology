package org.terasology.persistence.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.terasology.persistence.model.PasswordResetToken;
import org.terasology.persistence.model.User;
import org.terasology.persistence.model.VerificationToken;

import java.util.Date;
import java.util.stream.Stream;

public interface VerificationTokenRepository  extends CrudRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

    Stream<VerificationToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

    @Modifying
    @Query("delete from VerificationToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);

}
