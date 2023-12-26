package com.myblog9.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private long id;
    @NotEmpty
    @Size(min=10,message = "descrpition at least 10 words")
    private  String title;
    @NotEmpty
    private String description;
    @NotEmpty
    private String content;

}
