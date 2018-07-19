package models;

import java.time.LocalDateTime;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import io.ebean.Model;
import io.ebean.annotation.CreatedTimestamp;
import io.ebean.annotation.UpdatedTimestamp;

@MappedSuperclass
public class AppModel extends Model {
	/** ID */
    @Id
    public Long id;

    /** 作成日時 */
    @CreatedTimestamp
    public LocalDateTime createdDate;

    /** 最終更新日時 */
    @UpdatedTimestamp
    public LocalDateTime lastUpdatedDate;

}
