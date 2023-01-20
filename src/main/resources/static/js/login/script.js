
function forgotPassword(form){
			swal({
				title: "SENHA ENVIADA COM SUCESSO",
				text: "Verifique sua caixa de e-mail",
				icon: "success",
				
			})
			.then((isOkay) => {
				if (isOkay) {
					form.submit();
				}
			});
			return false;
		}
	

function newAccount(form){
			swal({
				title: "CONTA CADASTRADA COM SUCESSO",
				text: "Faça login para acessar sua conta",
				icon: "success",
				
			})
			.then((isOkay) => {
				if (isOkay) {
					form.submit();
				}
			});
			return false;
		}