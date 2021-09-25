# Zettel format

> ## \<zettel id /> - \<title />
> 
> ## tags: \#\<tag /> \#\<tag /> \#\<tag />
>
> ## created at: <date and time>
> 
> ## updated at: <date and time>
>
> ## \<content>
>
> The content goes after the header, the first two lines.
>
> This is a reference that can be placed along the content. [#\<reference id />]
> 
> In this implementation the reference, when used, shall not be placed in the start of the sentence.
> Otherwise it will be considered as a reference declaration.  
>
> [#\<reference id />] This is the declaration of the reference above and is described in a single line.
> It will be used as an index.
> It shall contain details such title, author, chapter, section, pages, and links.
>
> This is the way to mention an existing zettel [[#\<zettel id />]].
> If it exists will be used to index for search and statistics. 
> 
> ## \</content>
>
> ## \<footer>
>
> The footer contains all the references in the zettel.
> It can be done manually by the user or in a post-processing action. 
>
> ## \</footer>

# Post processing

In a post processing action, all the references can be cut and pasted in the end of the content.

The order can be by order of appearance and can be divided by external references (links, citations, etc) and zettels.

Non existing zettels can be marked with *(ne)* in the end.

Existing ones can also be followed by its title.

# Indexes

The system can provide a way to have all the indexes already processed after the insertion or update of zettels.

A file (eg. .indexes) can be used to keep the information and can be stored together with zettels.

A well-known format like JSON or YAML can be used to do the job.

Example:
    
    [
        {
            id: "20200925130700",
            title: "...",
            tags: ["tag1", "tag2", ...],
            references: {
                zettels: [
                    "20200925130500",
                    "20200925130600"
                ],
                citations: [
                    {
                        key: "miltonfriedman1962",
                        description: "Capitalism and freedom - Milton Friedman, University of Chicago Press, 1962"
                    },
                ]
            }
        }
    ]
 