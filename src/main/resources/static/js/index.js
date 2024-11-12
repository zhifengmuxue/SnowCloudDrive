function uploadFile(breadcrumb) {
    var path = '';
    for (var i = 0; i < breadcrumb.length; i++) {
        path += breadcrumb[i].folderName + '\\';
    }
    document.getElementById('breadcrumb').value = path;
    const uploadFileModal = new bootstrap.Modal(document.getElementById('uploadFileModal'));
    uploadFileModal.show();
}

function updateFileName() {
    const fileInput = document.getElementById('fileInput');
    const fileNameInput = document.getElementById('fileName');
    if (fileInput.files.length > 0) {
        fileNameInput.value = fileInput.files[0].name;
    } else {
        fileNameInput.value = '';
    }
}
function openRenameModal(fileId, currentName) {
    document.getElementById('fileId').value = fileId;
    document.getElementById('newName').value = currentName;
    const renameModal = new bootstrap.Modal(document.getElementById('renameModal'));
    renameModal.show();
}
function openDeleteConfirmModal(fileId) {
    document.getElementById('deleteFileId').value = fileId;
    const deleteConfirmModal = new bootstrap.Modal(document.getElementById('deleteConfirmModal'));
    deleteConfirmModal.show();
}

function openRenameFolderModal(folderId, currentFolderName) {
    document.getElementById('folderId').value = folderId;
    document.getElementById('newFolderName').value = currentFolderName;
    const renameFolderModal = new bootstrap.Modal(document.getElementById('renameFolderModal'));
    renameFolderModal.show();
}

function openDeleteFolderConfirmModal(folderId) {
    document.getElementById('deleteFolderId').value = folderId;
    const deleteFolderConfirmModal = new bootstrap.Modal(document.getElementById('deleteFolderConfirmModal'));
    deleteFolderConfirmModal.show();
}

function setFolderId(breadcrumb) {
    var path = '';
    for (var i = 0; i < breadcrumb.length; i++) {
        path += breadcrumb[i].folderName + '\\';
    }
    document.getElementById('folderBreadcrumb').value = path;
    const createFolderModal = new bootstrap.Modal(document.getElementById('createFolderModal'));
    createFolderModal.show();
}

function openShareFileModal(fileId, fileName) {
    document.getElementById('shareFileId').value = fileId;
    document.getElementById('shareLink').value = `${window.location.origin}/file/share?id=${fileId}`;

    const modal = new bootstrap.Modal(document.getElementById('shareFileModal'));
    modal.show();
}

function copyShareLink() {
    const shareLinkInput = document.getElementById('shareLink');
    shareLinkInput.select();
    shareLinkInput.setSelectionRange(0, 99999); // 对于移动设备

    navigator.clipboard.writeText(shareLinkInput.value).then(() => {
        alert("链接已复制到剪贴板！");
    }, (err) => {
        console.error('复制失败:', err);
    });
}