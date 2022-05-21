import {Component, Input, OnInit, Output} from '@angular/core';
import {PatentModel} from "../../model/patent-model";

@Component({
  selector: 'app-patent-details',
  templateUrl: './patent-details.component.html',
  styleUrls: ['./patent-details.component.scss']
})
export class PatentDetailsComponent {

  constructor() { }

  @Input()
  public patent: PatentModel | undefined;
}
