const showToastMessage = (vueInstance, title, msg, variant) => {
    vueInstance.$root.$bvToast.toast(msg, {
        title: title,
        variant: variant
    });
};

export { showToastMessage };
