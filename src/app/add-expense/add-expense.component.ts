import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-add-expense',
  templateUrl: './add-expense.component.html',
  styleUrls: ['./add-expense.component.css']
})
export class AddExpenseComponent implements OnInit {

  contactForm: FormGroup;

  constructor(private fb: FormBuilder){
    this.contactForm = fb.group({
      amount : '',
      note : '',
      tag : ''
    });
  }

  ngOnInit(): void {
  }

}
