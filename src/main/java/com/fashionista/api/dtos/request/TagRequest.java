package com.fashionista.api.dtos.request;

import com.fashionista.api.entities.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class TagRequest {
    @NotBlank(message = "Tag name is required.")
    private String name;
    @NotBlank(message = "Tag description is required.")
    private String description;
    @NotBlank(message = "Tag type is required.")
    private String type;
    @NotNull(message = "Tag image is required.")
    private MultipartFile image;

    public Tag transformToEntity() {
        return new Tag(this.name, this.type, this.description);
    }
}
