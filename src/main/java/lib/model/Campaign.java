package lib.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data @Getter @Setter
public class Campaign {
        private long id;
        private String name;
        private int user_id;
        private String is_published;
        private String is_stopped;
        private String is_edited;
        private int navigation_category_rule_id;
        private String is_outversioned;
        private String description;
        private String category_rule_id;
        private String Version_group;

}
