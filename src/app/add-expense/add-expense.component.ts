import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { TagService } from '../service/tag.service';

@Component({
  selector: 'app-add-expense',
  templateUrl: './add-expense.component.html',
  styleUrls: ['./add-expense.component.css']
})
export class AddExpenseComponent implements OnInit {

  contactForm: FormGroup;

  constructor(private fb: FormBuilder, private tagService: TagService){
    this.contactForm = fb.group({
      amount : '',
      note : '',
      tag : ''
    });
  }

  ngOnInit(): void {
    this.getAllTags('POSITIVE');
  }

  public getAllTags(kind: string): void {
    this.tagService.getAllTags(kind).subscribe(tags => console.log(tags));
  }

}
