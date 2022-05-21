import { Component, OnInit } from '@angular/core';
import { FavoritePatentService } from 'src/app/services/favorite-patent.service';
import { FavoritePatent } from '../../../model/favorite-patent';
import { HttpClient } from '@angular/common/http';
import { PatentInfo } from '../../../model/patent-info';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';

@Component({
    selector: 'app-my-patents',
    templateUrl: './my-patents.component.html',
    styleUrls: ['./my-patents.component.scss']
})
export class MyPatentsComponent implements OnInit {

    favoritePatents: FavoritePatent[] = [];
    errorMessage = '';
    patentsDetails: PatentInfo[] = [];
    /* An array of form is needed to bind each formGroup to each favorite patent displayed dynamically */
    formsEditFavorite: FormGroup[] = [];
    isUpdated: boolean[] = [];
    hasInternalError: boolean[] = [];

    constructor(private favoritePatentService: FavoritePatentService, private http: HttpClient, private formBuilder: FormBuilder,) {
    }

    ngOnInit(): void {
        this.getFavoritePatents();
    }

    /* This function will be called when the comment form is submitted */
    onSubmit(index: number) {
        /* Changing the comment of the patent related to its index with the comment inserted in the form input  */
        this.favoritePatents[index].comment = this.formsEditFavorite[index].controls['comment'].value;
        this.favoritePatents[index].patentTitle = this.formsEditFavorite[index].controls['patentTitle'].value;
        /* Calling the endpoint method to update the favorite patent in database */
        this.favoritePatentService.updateComment(this.favoritePatents[index]).subscribe(
            data => {
                this.isUpdated[index] = true;
            },
            error => console.log(error)
        );

    }

    /* This function will get all favorite patents from a user */
    getFavoritePatents() {
        this.favoritePatentService.getAllByUser().subscribe({
            next: (patents) => {
                this.favoritePatents = patents;
            },
            error: (err) => console.error(err)
        });

    }

    /* This function will delete a favorite patent */
    deleteFavoritePatent(id: number): void {
        let conf = confirm('Vous êtes sûr de retirer ce brevet des favoris ?');
        if (conf) {
            this.favoritePatentService.deleteFavoritePatent(id).subscribe(() => {
                window.location.reload();
            });
        }
    }

    /* This function will get all the information from a patent through EPO API. */
    getDetailsByPatentCode(index: number) {
        /* Checking if the patentsDetails is empty, so we can add the patent details collected from the API to this variable */
        if (this.patentsDetails.length == 0 || this.patentsDetails[index] == undefined) {
            /* Adding the publication code from the patent, collected from database, to the index passed as parameter */
            let patentCode = this.favoritePatents[index].publicationCode;

            /* Collecting the patent information through OPS API,
                 adding it to the variable patentsDetails,
                with the same index from the publication code, stocked in favoritePatent array */
            this.favoritePatentService.getPatentInfo(patentCode).subscribe({
                next: (patentInfo: PatentInfo) => {

                    this.patentsDetails[index] = patentInfo;
                    this.hasInternalError[index] = false;
                    /* Creating a new form for each patent recovered from database */
                    this.formsEditFavorite[index] = this.formBuilder.group({
                        /* For pubCod and patentTitle, the data is updated from EPO Api */
                        publicationCode: new FormControl(this.patentsDetails[index].patentNumber, [Validators.required]),
                        patentTitle: new FormControl(this.patentsDetails[index].title, [Validators.required]),
                        comment: new FormControl(this.favoritePatents[index].comment)
                    })

                },

                error: (err) => {
                  console.error(err)
                  this.hasInternalError[index] = true;
                }


            })
        }
    }
}
