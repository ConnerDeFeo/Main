import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { needType } from '../need';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-add-need-popup',
  templateUrl: './add-need-popup.component.html',
  styleUrl: './add-need-popup.component.css'
})
export class AddNeedPopupComponent {
  
  modelForm: FormGroup;
  needType = needType;
  wishlist = false;

  constructor(
    private formBuilder: FormBuilder,
    private dialogRef: MatDialogRef<AddNeedPopupComponent>,
    @Inject (MAT_DIALOG_DATA) public data: any,
  ){
    
    const maxQuantity = data.need.quantity;

    this.modelForm = this.formBuilder.group({
      name: [{value:data.need.name, disabled:true}, Validators.required],
      cost: [{value:data.need.cost,disabled:true}, Validators.required],
      quantity: [{value:data.need.quantity,disabled:false}, Validators.required],
      type: [{value:data.need.type,disabled:true}, Validators.required],
      isChecked: [false]
    });
    
    this.modelForm.get('quantity')?.valueChanges.subscribe(value => {
      const currentQuantity = this.modelForm.get('quantity')?.value;

      if (currentQuantity === null || currentQuantity === '') {
        return;
      }
      
      if (currentQuantity < 1) {
        this.modelForm.get('quantity')?.setValue(1);
      } else if (currentQuantity > maxQuantity) {
        this.modelForm.get('quantity')?.setValue(maxQuantity);
      }
    });

  }

  onSubmit(){
    if(this.modelForm.valid){
      this.dialogRef.close(this.modelForm.value);
    }
  }

  onCancel(){
    this.dialogRef.close()
  }
}
