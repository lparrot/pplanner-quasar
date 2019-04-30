<template>
  <section class="file-upload">
    <slot :fireChooseFile="fireChooseFile" />
    <input :accept="accept" @change="onFileUpload" ref="fileupload" style="display: none" type="file">
  </section>
</template>

<script>
export default {
  name: 'FileUpload',
  props: {
    accept: {
      type: String,
      default: 'image/*',
    },
  },
  data: () => ({
    title: 'Image Upload',
    dialog: false,
    imageName: '',
    imageUrl: '',
    imageFile: '',
  }),
  methods: {
    fireChooseFile () {
      this.$refs.fileupload.click()
    },
    onFileUpload (e) {
      const files = e.target.files
      if (files[0] !== undefined) {
        this.imageName = files[0].name
        if (this.imageName.lastIndexOf('.') <= 0) {
          return
        }
        const fr = new FileReader()
        fr.readAsDataURL(files[0])
        fr.addEventListener('load', () => {
          this.imageUrl = fr.result
          this.imageFile = files[0] // this is an image file that can be sent to server...

          this.$emit('on-upload', {
            name: this.imageName,
            url: this.imageUrl,
            file: this.imageFile,
          })
        })
      } else {
        this.imageName = ''
        this.imageFile = ''
        this.imageUrl = ''
      }
    },
  },
}
</script>
